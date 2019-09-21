import {HttpClient, HttpParams} from "@angular/common/http";
import {DateUtils} from "../shared/date-util";

export class OrderListFilter {
  params: HttpParams = new HttpParams();
private dateUtils:DateUtils;

  constructor(dateUtils: DateUtils, page: number = 0, size: number = 3, id: string = null, stateCode: string = null, clientName: string = null,
              fromCreatedAt: Date = null, toCreatedAt: Date = null,
              sort: string = '-createdAt') {
    this.dateUtils = dateUtils;
    this.id = id;
    this.stateCode = stateCode;
    this.clientName = clientName;
    this.fromCreatedAt = fromCreatedAt;
    this.toCreatedAt = toCreatedAt;
    this.sort = sort;
    this.page = page;
    this.size = size;
  }


  get id(): string {
    return this.params.get('id');
  }

  set id(value: string) {
    if(value)
    this.params = this.params.set('id', value);
  }

  get stateCode(): string {
    return this.params.get('stateCode');
  }

  set stateCode(value: string) {
    if(value)
    this.params = this.params.set('stateCode', value);
  }

  get clientName(): string {
    return this.params.get('clientName');
  }

  set clientName(value: string) {
    if(value!= null )
    this.params = this.params.set('clientName', value);
  }

  get fromCreatedAt(): Date {
    return new Date(this.params.get('fromCreatedAt'));
  }

  set fromCreatedAt(value: Date) {
    if (value) {
      let v =  this.dateUtils.convertDateToServerString(value);
      this.params = this.params.set('fromCreatedAt', v);
    }
  }

  get toCreatedAt(): Date {
    return new Date(this.params.get('toCreatedAt'));
  }

  set toCreatedAt(value: Date) {
    if (value) {
      this.params = this.params.set('toCreatedAt', value.toISOString());
    }
  }

  get sort(): string {
    return this.params.get('sort');
  }

  set sort(value: string) {
    if(value)
    this.params = this.params.set('sort', value);
  }

  get page(): number {
    return Number.parseInt(this.params.get('page'));
  }

  set page(value: number) {
    if(value != null)
    this.params = this.params.set('page', value.toString());
  }

  get size(): number {
    return Number.parseInt(this.params.get('size'));
  }

  set size(value: number) {
    if(value)
    this.params = this.params.set('size', value.toString());
  }
}
