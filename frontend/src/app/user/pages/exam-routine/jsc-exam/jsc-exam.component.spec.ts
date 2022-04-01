import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JscExamComponent } from './jsc-exam.component';

describe('JscExamComponent', () => {
  let component: JscExamComponent;
  let fixture: ComponentFixture<JscExamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JscExamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JscExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
