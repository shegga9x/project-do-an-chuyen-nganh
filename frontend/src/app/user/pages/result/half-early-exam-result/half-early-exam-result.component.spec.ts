import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HalfEarlyExamResultComponent } from './half-early-exam-result.component';

describe('HalfEarlyExamResultComponent', () => {
  let component: HalfEarlyExamResultComponent;
  let fixture: ComponentFixture<HalfEarlyExamResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HalfEarlyExamResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HalfEarlyExamResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
