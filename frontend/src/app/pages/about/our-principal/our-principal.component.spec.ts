import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OurPrincipalComponent } from './our-principal.component';

describe('OurPrincipalComponent', () => {
  let component: OurPrincipalComponent;
  let fixture: ComponentFixture<OurPrincipalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OurPrincipalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OurPrincipalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
