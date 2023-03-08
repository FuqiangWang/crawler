import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArcMetadataService } from '../service/arc-metadata.service';

import { ArcMetadataComponent } from './arc-metadata.component';

describe('ArcMetadata Management Component', () => {
  let comp: ArcMetadataComponent;
  let fixture: ComponentFixture<ArcMetadataComponent>;
  let service: ArcMetadataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'arc-metadata', component: ArcMetadataComponent }]), HttpClientTestingModule],
      declarations: [ArcMetadataComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ArcMetadataComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArcMetadataComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ArcMetadataService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.arcMetadata?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to arcMetadataService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getArcMetadataIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getArcMetadataIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
