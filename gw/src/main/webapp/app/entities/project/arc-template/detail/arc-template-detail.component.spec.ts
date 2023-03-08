import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcTemplateDetailComponent } from './arc-template-detail.component';

describe('ArcTemplate Management Detail Component', () => {
  let comp: ArcTemplateDetailComponent;
  let fixture: ComponentFixture<ArcTemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArcTemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ arcTemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ArcTemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ArcTemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load arcTemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.arcTemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
