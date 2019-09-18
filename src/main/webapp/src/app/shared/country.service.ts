import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client, Country} from "./models";

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private resourceUrl = 'api/country';
  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Country[]> {
    return this.http.get<Country[]>(this.resourceUrl , {});
  }
}
