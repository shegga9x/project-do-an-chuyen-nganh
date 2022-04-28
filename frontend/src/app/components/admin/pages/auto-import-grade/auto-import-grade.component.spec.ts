import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutoImportGradeComponent } from './auto-import-grade.component';

describe('AutoImportGradeComponent', () => {
  let component: AutoImportGradeComponent;
  let fixture: ComponentFixture<AutoImportGradeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutoImportGradeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutoImportGradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
