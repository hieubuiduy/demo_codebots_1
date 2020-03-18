import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomReferenceFilterComponent } from './custom-reference-filter.component';

describe('CustomReferenceFilterComponent', () => {
  let component: CustomReferenceFilterComponent;
  let fixture: ComponentFixture<CustomReferenceFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomReferenceFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomReferenceFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
