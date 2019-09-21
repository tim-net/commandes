import {AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {OrderListFilter} from "../order-list.filter";
import {OrderService} from "../../shared/order.service";
import {OrderState} from "../../order-edit/order-model";
import {fromEvent} from "rxjs";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";

@Component({
  selector: 'order-list-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class OrderListFilterComponent implements OnInit, AfterViewInit {
  @ViewChild('clientNameInput', null) clientNameInput: ElementRef;
  orderStates: OrderState[];
  clientNameFilter:string;
  fromCreatedAtFilter:Date;
  stateCodeFilter: string;
  @Output() onClientChange = new EventEmitter<string>();
  @Output() onFromCreatedAtFilterChange = new EventEmitter<Date>();
  @Output() onStateCodeFilterChange = new EventEmitter<string>();

  constructor(private orderService: OrderService) {
  }

  ngOnInit() {
    this.orderService.getOrderStates().subscribe(value => this.orderStates = value);
  }


  ngAfterViewInit(): void {
    fromEvent(this.clientNameInput.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.onClientChange.emit(this.clientNameFilter);
        })
      )
      .subscribe();
  }

  fromCreatedAtFilterChanged() {
    this.onFromCreatedAtFilterChange.emit(this.fromCreatedAtFilter);
  }

  stateCodeFilterChanged() {
    this.onStateCodeFilterChange.emit(this.stateCodeFilter);
  }
}
