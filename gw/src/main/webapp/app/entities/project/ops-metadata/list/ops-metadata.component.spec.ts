import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OpsMetadataService } from '../service/ops-metadata.service';

import { OpsMetadataComponent } from './ops-metadata.component';

describe('OpsMetadata Management Component', () => {
  let comp: OpsMetadataComponent;
  let fixture: ComponentFixture<OpsMetadataComponent>;
  let service: OpsMetadataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'ops-metadata', component: OpsMetadataComponent }]), HttpClientTestingModule],
      declarations: [OpsMetadataComponent],
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
      .overrideTemplate(OpsMetadataComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OpsMetadataComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OpsMetadataService);

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
    expect(comp.opsMetadata?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to opsMetadataService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getOpsMetadataIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getOpsMetadataIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
