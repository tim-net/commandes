import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {OrderModel} from "../order-edit/order-model";
import {Client} from "./models";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private resourceUrl = 'api/client';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.resourceUrl , {});
  }

}
