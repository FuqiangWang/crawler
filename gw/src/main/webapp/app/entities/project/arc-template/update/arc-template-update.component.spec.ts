import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArcTemplateFormService } from './arc-template-form.service';
import { ArcTemplateService } from '../service/arc-template.service';
import { IArcTemplate } from '../arc-template.model';

import { ArcTemplateUpdateComponent } from './arc-template-update.component';

describe('ArcTemplate Management Update Component', () => {
  let comp: ArcTemplateUpdateComponent;
  let fixture: ComponentFixture<ArcTemplateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let arcTemplateFormService: ArcTemplateFormService;
  let arcTemplateService: ArcTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArcTemplateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ArcTemplateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArcTemplateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    arcTemplateFormService = TestBed.inject(ArcTemplateFormService);
    arcTemplateService = TestBed.inject(ArcTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const arcTemplate: IArcTemplate = { id: 456 };

      activatedRoute.data = of({ arcTemplate });
      comp.ngOnInit();

      expect(comp.arcTemplate).toEqual(arcTemplate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcTemplate>>();
      const arcTemplate = { id: 123 };
      jest.spyOn(arcTemplateFormService, 'getArcTemplate').mockReturnValue(arcTemplate);
      jest.spyOn(arcTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arcTemplate }));
      saveSubject.complete();

      // THEN
      expect(arcTemplateFormService.getArcTemplate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(arcTemplateService.update).toHaveBeenCalledWith(expect.objectContaining(arcTemplate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcTemplate>>();
      const arcTemplate = { id: 123 };
      jest.spyOn(arcTemplateFormService, 'getArcTemplate').mockReturnValue({ id: null });
      jest.spyOn(arcTemplateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcTemplate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arcTemplate }));
      saveSubject.complete();

      // THEN
      expect(arcTemplateFormService.getArcTemplate).toHaveBeenCalled();
      expect(arcTemplateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcTemplate>>();
      const arcTemplate = { id: 123 };
      jest.spyOn(arcTemplateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcTemplate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(arcTemplateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
