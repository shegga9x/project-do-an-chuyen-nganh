import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageEntitesComponent } from './manage-entites.component';

describe('ManageEntitesComponent', () => {
  let component: ManageEntitesComponent;
  let fixture: ComponentFixture<ManageEntitesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageEntitesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageEntitesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
