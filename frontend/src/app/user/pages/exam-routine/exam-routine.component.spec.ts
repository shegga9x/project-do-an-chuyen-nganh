import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamRoutineComponent } from './exam-routine.component';

describe('ExamRoutineComponent', () => {
  let component: ExamRoutineComponent;
  let fixture: ComponentFixture<ExamRoutineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamRoutineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamRoutineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
