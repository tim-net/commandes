import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {OrderService} from "../shared/order.service";
import {merge} from "rxjs";
import {OrderListFilter} from "./order-list.filter";
import {tap} from "rxjs/operators";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {OrderListDataSource} from "./utils/order-list-datasource";
import {OrderListFilterComponent} from "./filter/filter.component";
import {DateUtils} from "../shared/date-util";


@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit, AfterViewInit {
  dataSource: OrderListDataSource;
  filter: OrderListFilter;
  displayedColumns = ["id", "createdAt", "client", "shippingCountry", "state", "price"];
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;
  @ViewChild(OrderListFilterComponent, null) filterComponent: OrderListFilterComponent;

  constructor(private orderService: OrderService,private dateUtils: DateUtils) {
  this.filter = new OrderListFilter(dateUtils);
  }


  ngOnInit() {
    this.dataSource = new OrderListDataSource(this.orderService);
    this.loadOrdersPage();
  }

  ngAfterViewInit() {
// this.filter = this.filterComponent.filter;
    // reset the paginator after sorting
    this.sort.sortChange.subscribe(() => this.filter.page = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.loadOrdersPage())
      )
      .subscribe();
  }


  private loadOrdersPage() {
    this.filter.sort = this.sort.direction == 'asc' ? "+" : "-" + this.sort.active;
    this.filter.page = this.paginator.pageIndex;
    this.filter.size = this.paginator.pageSize;
    this.dataSource.loadOrders(this.filter);
  }

  onClientChange($event: string) {
    this.filter.clientName = $event;
    this.loadOrdersPage();
  }

  onFromCreatedAtFilterChange($event: Date) {
    this.filter.fromCreatedAt = $event;
    this.loadOrdersPage();
  }

  onStateCodeFilterChange($event: string) {
    this.filter.stateCode = $event;
    this.loadOrdersPage();
  }
}
