import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {OrderModel} from "../order-edit/order-model";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private resourceUrl = 'api/order';

  constructor(private http: HttpClient) {
  }

  find(id: number): Observable<any> {
    return this.http.get(this.resourceUrl + "/" + id, {});
  }

  save(order: OrderModel): Observable<boolean> {
    return this.http.put<boolean>(this.resourceUrl, order);
  }

}
