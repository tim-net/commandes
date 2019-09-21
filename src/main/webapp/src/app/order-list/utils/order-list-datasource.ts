import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {OrderLisOrder} from "../order-list.model";
import {BehaviorSubject, Observable} from "rxjs";
import {OrderService} from "../../shared/order.service";
import {OrderListFilter} from "../order-list.filter";

export class OrderListDataSource implements DataSource<OrderLisOrder> {

  private subject = new BehaviorSubject<OrderLisOrder[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  ordersCount: number;


  constructor(private orderService: OrderService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<OrderLisOrder[] | ReadonlyArray<OrderLisOrder>> {
    return this.subject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.subject.complete();
    this.loadingSubject.complete();
  }

  loadOrders(filter: OrderListFilter) {
    this.loadingSubject.next(true);
    this.orderService.findAll(filter)
      .subscribe(orderData => {
        this.ordersCount = orderData.count;
        this.subject.next(orderData.orders);
        this.loadingSubject.next(false);
      });
  }

}
