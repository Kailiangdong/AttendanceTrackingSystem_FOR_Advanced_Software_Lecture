import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorhomeComponent } from './tutorhome.component';

describe('TutorhomeComponent', () => {
  let component: TutorhomeComponent;
  let fixture: ComponentFixture<TutorhomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TutorhomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorhomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
