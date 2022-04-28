import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportDateExamComponent } from './report-date-exam.component';

describe('ReportDateExamComponent', () => {
  let component: ReportDateExamComponent;
  let fixture: ComponentFixture<ReportDateExamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportDateExamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportDateExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
