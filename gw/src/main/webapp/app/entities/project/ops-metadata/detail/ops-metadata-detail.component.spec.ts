import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpsMetadataDetailComponent } from './ops-metadata-detail.component';

describe('OpsMetadata Management Detail Component', () => {
  let comp: OpsMetadataDetailComponent;
  let fixture: ComponentFixture<OpsMetadataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OpsMetadataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ opsMetadata: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OpsMetadataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OpsMetadataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load opsMetadata on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.opsMetadata).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
