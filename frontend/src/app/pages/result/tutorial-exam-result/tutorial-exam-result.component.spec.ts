import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorialExamResultComponent } from './tutorial-exam-result.component';

describe('TutorialExamResultComponent', () => {
  let component: TutorialExamResultComponent;
  let fixture: ComponentFixture<TutorialExamResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TutorialExamResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorialExamResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
