import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JscExamResultComponent } from './jsc-exam-result.component';

describe('JscExamResultComponent', () => {
  let component: JscExamResultComponent;
  let fixture: ComponentFixture<JscExamResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JscExamResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JscExamResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
