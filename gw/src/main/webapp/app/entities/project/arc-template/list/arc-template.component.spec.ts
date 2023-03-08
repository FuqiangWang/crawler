import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArcTemplateService } from '../service/arc-template.service';

import { ArcTemplateComponent } from './arc-template.component';

describe('ArcTemplate Management Component', () => {
  let comp: ArcTemplateComponent;
  let fixture: ComponentFixture<ArcTemplateComponent>;
  let service: ArcTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'arc-template', component: ArcTemplateComponent }]), HttpClientTestingModule],
      declarations: [ArcTemplateComponent],
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
      .overrideTemplate(ArcTemplateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArcTemplateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ArcTemplateService);

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
    expect(comp.arcTemplates?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to arcTemplateService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getArcTemplateIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getArcTemplateIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
