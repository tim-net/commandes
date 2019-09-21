export interface OrderListModel {
  count: number;
  orders: OrderLisOrder[];
}

export interface OrderLisOrder {
  id: number;
  createdAt: string;
  client: string;
  state: string;
  shippingCountry: string;
  price: number
}
