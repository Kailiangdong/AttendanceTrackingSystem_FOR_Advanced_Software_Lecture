import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorlistComponent } from './tutorlist.component';

describe('TutorlistComponent', () => {
  let component: TutorlistComponent;
  let fixture: ComponentFixture<TutorlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TutorlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TutorlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
