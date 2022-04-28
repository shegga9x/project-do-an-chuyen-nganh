import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseRegistComponent } from './course-regist.component';

describe('CourseRegistComponent', () => {
  let component: CourseRegistComponent;
  let fixture: ComponentFixture<CourseRegistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseRegistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseRegistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
