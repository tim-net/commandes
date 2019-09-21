import {Article, Client, OrderOutcome} from "../shared/models";

export class OrderModel {
  id: number;
  created: string;
  state: OrderState;
  lines: OrderLine[];
  price: number;
  shippingCountry: string;
  client: Client;
}

export interface OrderState {
  code : string;
  label : string;
  outcome: OrderOutcome;
}

export class OrderLine {
  id: number;
  article: Article;
  price : number;
  amount: number;
  editing: boolean;
  private _isNew: boolean = false;


  constructor(id: number = null, article: Article, price: number, amount: number = 1) {
    this.id = id;
    this.article = article;
    this.price = price;
    this.amount = amount;
  }

  get isNew(): boolean {
    return this._isNew;
  }

  set isNew(value: boolean) {
    this._isNew = value;
  }
}


