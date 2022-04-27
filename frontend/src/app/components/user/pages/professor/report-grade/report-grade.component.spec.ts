import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportGradeComponent } from './report-grade.component';

describe('ReportGradeComponent', () => {
  let component: ReportGradeComponent;
  let fixture: ComponentFixture<ReportGradeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportGradeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportGradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
