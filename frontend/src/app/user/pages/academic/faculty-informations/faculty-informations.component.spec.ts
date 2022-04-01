import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacultyInformationsComponent } from './faculty-informations.component';

describe('FacultyInformationsComponent', () => {
  let component: FacultyInformationsComponent;
  let fixture: ComponentFixture<FacultyInformationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FacultyInformationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FacultyInformationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
