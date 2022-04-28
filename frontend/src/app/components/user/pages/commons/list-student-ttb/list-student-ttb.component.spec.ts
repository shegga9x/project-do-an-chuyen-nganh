import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStudentTTBComponent } from './list-student-ttb.component';

describe('ListStudentTTBComponent', () => {
  let component: ListStudentTTBComponent;
  let fixture: ComponentFixture<ListStudentTTBComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStudentTTBComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStudentTTBComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
