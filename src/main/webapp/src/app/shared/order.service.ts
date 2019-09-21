import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {OrderModel, OrderState} from "../order-edit/order-model";
import {OrderListFilter} from "../order-list/order-list.filter";
import {OrderListModel} from "../order-list/order-list.model";
import {DateUtils} from "./date-util";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private resourceUrl = 'api/order';

  constructor(private http: HttpClient,private dateUtils: DateUtils) {
  }

  find(id: number): Observable<OrderModel> {
    return this.http.get<OrderModel>(this.resourceUrl + "/" + id, {});
  }

  save(order: OrderModel): Observable<boolean> {
    return this.http.put<boolean>(this.resourceUrl, order);
  }

  findAll(filter: OrderListFilter): Observable<OrderListModel> {
    filter.params.set('fromCreatedAt', this.dateUtils.convertDateToServerString(filter.fromCreatedAt));
    return this.http.get<OrderListModel>(this.resourceUrl, {params: filter.params});
  }

  getOrderStates() :Observable<OrderState[]> {
    return this.http.get<OrderState[]>(this.resourceUrl + "/states", {});
  }
}
