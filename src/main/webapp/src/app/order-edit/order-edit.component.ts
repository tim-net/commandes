import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {OrderLine, OrderModel} from "./order-model";
import {OrderService} from "../shared/order.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {Article, Client, Country} from "../shared/models";
import {ClientService} from "../shared/client.service";
import {CountryService} from "../shared/country.service";
import {ArticleService} from "../shared/article.service";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import {MatTable, MatTableDataSource} from "@angular/material/table";

class ArticlesDataSource implements DataSource<Article> {
  private articlesSubject = new BehaviorSubject<Article[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  constructor(private articleService: ArticleService) {
  }

  public loading$ = this.loadingSubject.asObservable();

  connect(collectionViewer: CollectionViewer): Observable<Article[] | ReadonlyArray<Article>> {
    return this.articlesSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.articlesSubject.complete();
    this.loadingSubject.complete();
  }
  reset() {
    this.articlesSubject.next([]);
  }

  loadArticles() {
    this.loadingSubject.next(true);

    this.articleService.getAll().pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(articles => this.articlesSubject.next(articles));

  }
}

class OrderLineDatasource implements DataSource<OrderLine> {
  private orderLineSubject;
  public orderLines$: Observable<OrderLine[]>;

  constructor(private order: OrderModel) {
    this.orderLineSubject = new BehaviorSubject<OrderLine[]>(order.lines);
    this.orderLines$ = this.orderLineSubject.asObservable();
  }

  connect(collectionViewer: CollectionViewer): Observable<OrderLine[] | ReadonlyArray<OrderLine>> {
    return this.orderLineSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.orderLineSubject.complete();
  }

  public addArticle(article: Article) {
    if (this.order.lines.find(l => l.article.code == article.code)) {
      let orderLine = this.order.lines.find(l => l.article.code == article.code);
      orderLine.amount++;
      orderLine.price+=article.price;
      this.order.price += article.price;
    } else {
      let orderLine1 = new OrderLine(null, article, article.price);
      this.order.lines.push(orderLine1);
      this.order.price += article.price;
      this.orderLineSubject.next(this.order.lines);
    }
  }

  public removeLine(line: OrderLine) {
    this.order.lines = this.order.lines.filter(l => l.article.code == line.article.code);
    this.orderLineSubject.next(this.order.lines);
  }

}

@Component({
  selector: 'order',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {
  public order: OrderModel;
  private _saveOrderStatus: SaveStatus;
  clients: Client[];
  countries: Country[];

  constructor(private service: OrderService,
              private clientService: ClientService,
              private countryService: CountryService,
              private articleService: ArticleService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  dataSourceArticles: ArticlesDataSource;
  @ViewChild("tableOrderLines", null)
  tableOrderLines: MatTable<OrderLine>;
  @ViewChild("tableArticles", null)
  tableArticles: MatTable<Article>;
  dataSourceOrderLines: OrderLineDatasource;
  articlesDisplayedColumns = ["code", "label", "familyCode", "price"];
  orderLinesDisplayedColumns = ["article", "price", "amount"];


  ngOnInit() {
    this.getOrder();
    this.dataSourceArticles = new ArticlesDataSource(this.articleService);
    this.dataSourceArticles.loadArticles();
    this.loadClients();
    this.loadCountries();
  }

  goBack(): void {
    this.location.back();
  }

  saveOrder(): void {
    this.service.save(this.order).subscribe(r => this.onSaveOrder(r));
  }

  onSaveOrder(result: boolean) {
    if (result) {
      this._saveOrderStatus = SaveStatus.SUCCESS;
    } else {
      this._saveOrderStatus = SaveStatus.ERROR;
    }
  }

  private getOrder(): void {
    const id: number = Number.parseInt(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.service.find(id).subscribe(c => {
        this.order = c;
        this.order.lines = c.lines.map(n => new OrderLine(n.id, n.article, n.price, n.amount));
        this.createOrderLineDS();
      })
    } else {
      this.order = new OrderModel();
      this.order.price = 0.0;
      this.order.lines = [];
      this.createOrderLineDS();
    }
  }


  createOrderLineDS() {
    this.dataSourceOrderLines = new OrderLineDatasource(this.order);
    this.dataSourceOrderLines.orderLines$.subscribe(value => {
      if (value.length > 0) {
        this.tableArticles.renderRows();
      }
    });
  }

  getSaveOrderStatus(): SaveStatus {
    return this._saveOrderStatus;
  }



  private loadClients() {
    this.clientService.getAll().subscribe(value => this.clients = value)
  }

  private loadCountries() {
    this.countryService.getAll().subscribe(value => this.countries = value)
  }

  orderAddLine(row: Article) {
    this.dataSourceOrderLines.addArticle(row);
  }

  orderLinesContains(row: Article) {
    return this.order.lines.find(l => l.article.code == row.code) != undefined;
  }
}

export enum SaveStatus {
  SUCCESS = "SUCCESS", ERROR = "ERROR"
}
