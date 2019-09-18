import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "./models";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private resourceUrl = 'api/article';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.resourceUrl , {});
  }

}
