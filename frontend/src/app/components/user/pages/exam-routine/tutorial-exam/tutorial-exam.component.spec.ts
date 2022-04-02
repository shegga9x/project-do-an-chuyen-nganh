import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorialExamComponent } from './tutorial-exam.component';

describe('TutorialExamComponent', () => {
  let component: TutorialExamComponent;
  let fixture: ComponentFixture<TutorialExamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TutorialExamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorialExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
