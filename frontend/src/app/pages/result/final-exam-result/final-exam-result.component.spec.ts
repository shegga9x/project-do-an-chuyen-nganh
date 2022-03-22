import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalExamResultComponent } from './final-exam-result.component';

describe('FinalExamResultComponent', () => {
  let component: FinalExamResultComponent;
  let fixture: ComponentFixture<FinalExamResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FinalExamResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalExamResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
