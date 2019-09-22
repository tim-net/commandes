import {Component, OnInit, ViewChild} from '@angular/core';
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
import {MatTable} from "@angular/material/table";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {validateLines} from "./utils";
import {MatSnackBar} from "@angular/material/snack-bar";

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
      orderLine.price += article.price;
      this.order.price += article.price;
    } else {
      let orderLine1 = new OrderLine(null, article, article.price);
      this.order.lines.push(orderLine1);
      this.order.price += article.price;
      this.orderLineSubject.next(this.order.lines);
    }
  }

  public removeLine(line: OrderLine) {
    this.order.price -= line.price;
    this.order.lines = this.order.lines.filter(l => l.article.code != line.article.code);
    this.orderLineSubject.next(this.order.lines);
  }

}

@Component({
  selector: 'order',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {
  order: OrderModel;
  form: FormGroup;
  loadPending: boolean = true;
  clients: Client[];
  countries: Country[];

  constructor(private service: OrderService,
              private clientService: ClientService,
              private countryService: CountryService,
              private articleService: ArticleService,
              private route: ActivatedRoute,
              private _snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
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

  initForm() {
    this.form = this.formBuilder.group({
      client: [this.order.client, Validators.required],
      shippingCountry: [this.order.shippingCountry, Validators.required],
      lines: [this.order.lines, validateLines],
      price: [this.order.price],
      id: [this.order.id],
      state: [this.order.state],
      createdAt: []
    });
  }


  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    this.order.client = this.form.value.client;
    this.order.shippingCountry = this.form.value.shippingCountry;
    this.form.patchValue(this.order);
    this.service.save(this.form.value).subscribe(r => this.onSaveOrder(r));
  }

  onSaveOrder(result: boolean) {
    let msg;
    if (result) {
      msg = "Commande est sauvegardee";
    } else {
      msg = "Un erreur s'est produit.Desole."
    }
    this._snackBar.open(msg, null, {
      duration: 15000,
    });
  }

  compareByCodeFn: ((f1: any, f2: any) => boolean) | null = this.compareByCode;

  compareByCode(f1: any, f2: any) {
    return f1 && f2 && f1.code === f2.code;
  }

  private getOrder(): void {
    const id: number = Number.parseInt(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.service.find(id).subscribe(c => {
        this.order = c;
        this.initForm();
        this.createOrderLineDS();
        this.loadPending = false;
      })
    } else {
      this.order = new OrderModel();
      this.order.price = 0.0;
      this.order.lines = [];
      this.initForm();
      this.createOrderLineDS();
      this.loadPending = false;
    }
  }


  createOrderLineDS() {
    this.dataSourceOrderLines = new OrderLineDatasource(this.order);
    this.dataSourceOrderLines.orderLines$.subscribe(value => {
      if (this.tableArticles && value && value.length > 0) {
        this.tableArticles.renderRows();
      }
    });
  }



  private loadClients() {
    this.clientService.getAll().subscribe(value => this.clients = value)
  }

  private loadCountries() {
    this.countryService.getAll().subscribe(value => this.countries = value)
  }

  orderAddLine(row: Article) {
    this.dataSourceOrderLines.addArticle(row);
    this.form.controls['lines'].markAsTouched();
    this.form.patchValue(this.order);
  }

  removeLine(row: OrderLine) {
    this.dataSourceOrderLines.removeLine(row);
    this.form.patchValue(this.order);
  }

  orderLinesContains(row: Article) {
    return this.order.lines.find(l => l.article.code == row.code) != undefined;
  }

}

