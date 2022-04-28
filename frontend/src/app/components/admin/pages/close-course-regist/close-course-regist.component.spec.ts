import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseCourseRegistComponent } from './close-course-regist.component';

describe('CloseCourseRegistComponent', () => {
  let component: CloseCourseRegistComponent;
  let fixture: ComponentFixture<CloseCourseRegistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CloseCourseRegistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CloseCourseRegistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
