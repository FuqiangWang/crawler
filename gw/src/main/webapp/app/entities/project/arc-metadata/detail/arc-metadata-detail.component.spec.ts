import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcMetadataDetailComponent } from './arc-metadata-detail.component';

describe('ArcMetadata Management Detail Component', () => {
  let comp: ArcMetadataDetailComponent;
  let fixture: ComponentFixture<ArcMetadataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArcMetadataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ arcMetadata: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ArcMetadataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ArcMetadataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load arcMetadata on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.arcMetadata).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
