import {Component, OnInit} from '@angular/core';
import {OrderLine, OrderModel} from "./order-model";
import {OrderService} from "../shared/order.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {GridOptions} from "ag-grid";
import {Article, Client, Country} from "../shared/models";
import {ClientService} from "../shared/client.service";
import {CountryService} from "../shared/country.service";
import {ArticleService} from "../shared/article.service";

@Component({
  selector: 'order',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {
  public order: OrderModel;
  private _saveOrderStatus: SaveStatus;
  gridOptions: GridOptions = <GridOptions>{
    onModelUpdated: () => {
      this.gridOptions.api.sizeColumnsToFit();
    },
    onGridSizeChanged: () => {
      this.gridOptions.api.sizeColumnsToFit();
    }
  };
  orderLineColumnDefs: any[];
  clients: Client[];
  countries: Country[];
  constructor(private service: OrderService,
              private clientService: ClientService,
              private countryService: CountryService,
              private articleSService: ArticleService,
              private route: ActivatedRoute,
              private location: Location) {
  }
  articles: Article[];

  private createOrderLineColumnDefs() {
    return [
      {headerName: "Article", field: "article.name",  width: 500},
      {headerName: "Price", field: "price", width: 200},
      {headerName: "Amount", field: "amount", width: 200}
      ];
  }


  ngOnInit() {
    this.getOrder();
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
      })
    } else {
      this.order = new OrderModel();
    }
  }

  addNote(): void {
    // let customerNote = new OrderLine();
    // customerNote.isNew = true;
    // customerNote.editing = true;
    // this.order.notes.push(customerNote);
  }

  editNote(note: OrderLine): void {
    // note.editing = true
    // note.isNew = false;
  }


  get saveOrderStatus(): SaveStatus {
    return this._saveOrderStatus;
  }

  // customerStatusKeys(): string[] {
  //   return Object.keys(Order);
  // }
  //
  // customerStatusValue(key: string) {
  //   return Order[key];
  // }
  private loadClients() {
    this.clientService.getAll().subscribe(value => this.clients = value)
  }

  private loadCountries() {
    this.countryService.getAll().subscribe(value => this.countries = value)
  }
}

export enum SaveStatus {
  SUCCESS = "SUCCESS", ERROR = "ERROR"
}
