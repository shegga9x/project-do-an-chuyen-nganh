import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutoImportAccountComponent } from './auto-import-account.component';

describe('AutoImportAccountComponent', () => {
  let component: AutoImportAccountComponent;
  let fixture: ComponentFixture<AutoImportAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutoImportAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutoImportAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
