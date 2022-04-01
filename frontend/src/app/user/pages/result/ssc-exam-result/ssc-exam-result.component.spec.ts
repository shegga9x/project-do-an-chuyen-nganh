import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SscExamResultComponent } from './ssc-exam-result.component';

describe('SscExamResultComponent', () => {
  let component: SscExamResultComponent;
  let fixture: ComponentFixture<SscExamResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SscExamResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SscExamResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
