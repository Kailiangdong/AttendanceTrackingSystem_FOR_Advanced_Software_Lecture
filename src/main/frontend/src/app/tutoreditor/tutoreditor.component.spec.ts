import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TutoreditorComponent } from './tutoreditor.component';

describe('TutoreditorComponent', () => {
  let component: TutoreditorComponent;
  let fixture: ComponentFixture<TutoreditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TutoreditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TutoreditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
