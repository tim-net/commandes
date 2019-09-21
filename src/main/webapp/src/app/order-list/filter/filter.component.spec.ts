import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderListFilterComponent } from './filter.component';

describe('FilterComponent', () => {
  let component: OrderListFilterComponent;
  let fixture: ComponentFixture<OrderListFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderListFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderListFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
