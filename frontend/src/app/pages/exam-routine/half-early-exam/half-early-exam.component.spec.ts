import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HalfEarlyExamComponent } from './half-early-exam.component';

describe('HalfEarlyExamComponent', () => {
  let component: HalfEarlyExamComponent;
  let fixture: ComponentFixture<HalfEarlyExamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HalfEarlyExamComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HalfEarlyExamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
