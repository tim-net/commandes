import {OrderService} from "../shared/order.service";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BrowserModule} from "@angular/platform-browser";
import {OrderListComponent} from "./order-list.component";
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {OrderListFilterModule} from "./filter/filter.module";
import {DateUtils} from "../shared/date-util";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    OrderListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    OrderListFilterModule,
    MatButtonModule,
    RouterModule
  ],
  providers: [
    OrderService, DateUtils
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderListModule {

}
