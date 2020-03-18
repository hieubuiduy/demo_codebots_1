import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeciesReferenceFilterComponent } from './species-reference-filter.component';

describe('SpeciesReferenceFilterComponent', () => {
  let component: SpeciesReferenceFilterComponent;
  let fixture: ComponentFixture<SpeciesReferenceFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeciesReferenceFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeciesReferenceFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
