import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SscExamComponent } from './ssc-exam.component';

describe('SscExanComponent', () => {
  let component: SscExamComponent;
  let fixture: ComponentFixture<SscExamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SscExamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SscExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
