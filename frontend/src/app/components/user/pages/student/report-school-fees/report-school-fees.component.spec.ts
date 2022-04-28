import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportSchoolFeesComponent } from './report-school-fees.component';

describe('ReportSchoolFeesComponent', () => {
  let component: ReportSchoolFeesComponent;
  let fixture: ComponentFixture<ReportSchoolFeesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportSchoolFeesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportSchoolFeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
