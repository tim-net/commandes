import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {OrderEditComponent} from './order-edit/order-edit.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {OrderEditModule} from "./order-edit/order-edit.module";
import {OrderListComponent} from './order-list/order-list.component';
import {OrderListModule} from "./order-list/order-list.module";
import { OrderListFilterComponent } from './order-list/filter/filter.component';

const appRoutes: Routes = [
  {
    path: 'orders-list',
    component: OrderListComponent,
    data: {
      pageTitle: 'Orders'
    }
  }, {
    path: 'order/new',
    component: OrderEditComponent,
    data: {
      pageTitle: 'Order creation'
    }
  }, {
    path: 'order/edit/:id',
    component: OrderEditComponent,
    data: {
      pageTitle: 'Order modification'
    }
  },
  {
    path: '',
    redirectTo: 'orders-list',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    BrowserAnimationsModule,
    OrderEditModule,
    OrderListModule
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
