import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {OrderEditComponent} from "./order-edit.component";
import {OrderService} from "../shared/order.service";
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MatSelectModule
  ],
  declarations: [
    OrderEditComponent
  ],
  providers: [
    OrderService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderEditModule {

}
