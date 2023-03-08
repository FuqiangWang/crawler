import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArcMetadataFormService } from './arc-metadata-form.service';
import { ArcMetadataService } from '../service/arc-metadata.service';
import { IArcMetadata } from '../arc-metadata.model';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';
import { IArcTemplate } from 'app/entities/project/arc-template/arc-template.model';
import { ArcTemplateService } from 'app/entities/project/arc-template/service/arc-template.service';

import { ArcMetadataUpdateComponent } from './arc-metadata-update.component';

describe('ArcMetadata Management Update Component', () => {
  let comp: ArcMetadataUpdateComponent;
  let fixture: ComponentFixture<ArcMetadataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let arcMetadataFormService: ArcMetadataFormService;
  let arcMetadataService: ArcMetadataService;
  let projectMetadataService: ProjectMetadataService;
  let arcTemplateService: ArcTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArcMetadataUpdateComponent],
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
      .overrideTemplate(ArcMetadataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArcMetadataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    arcMetadataFormService = TestBed.inject(ArcMetadataFormService);
    arcMetadataService = TestBed.inject(ArcMetadataService);
    projectMetadataService = TestBed.inject(ProjectMetadataService);
    arcTemplateService = TestBed.inject(ArcTemplateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call projectMetadata query and add missing value', () => {
      const arcMetadata: IArcMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 444 };
      arcMetadata.projectMetadata = projectMetadata;

      const projectMetadataCollection: IProjectMetadata[] = [{ id: 69292 }];
      jest.spyOn(projectMetadataService, 'query').mockReturnValue(of(new HttpResponse({ body: projectMetadataCollection })));
      const expectedCollection: IProjectMetadata[] = [projectMetadata, ...projectMetadataCollection];
      jest.spyOn(projectMetadataService, 'addProjectMetadataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ arcMetadata });
      comp.ngOnInit();

      expect(projectMetadataService.query).toHaveBeenCalled();
      expect(projectMetadataService.addProjectMetadataToCollectionIfMissing).toHaveBeenCalledWith(
        projectMetadataCollection,
        projectMetadata
      );
      expect(comp.projectMetadataCollection).toEqual(expectedCollection);
    });

    it('Should call ArcTemplate query and add missing value', () => {
      const arcMetadata: IArcMetadata = { id: 456 };
      const arcTemplate: IArcTemplate = { id: 82156 };
      arcMetadata.arcTemplate = arcTemplate;

      const arcTemplateCollection: IArcTemplate[] = [{ id: 56463 }];
      jest.spyOn(arcTemplateService, 'query').mockReturnValue(of(new HttpResponse({ body: arcTemplateCollection })));
      const additionalArcTemplates = [arcTemplate];
      const expectedCollection: IArcTemplate[] = [...additionalArcTemplates, ...arcTemplateCollection];
      jest.spyOn(arcTemplateService, 'addArcTemplateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ arcMetadata });
      comp.ngOnInit();

      expect(arcTemplateService.query).toHaveBeenCalled();
      expect(arcTemplateService.addArcTemplateToCollectionIfMissing).toHaveBeenCalledWith(
        arcTemplateCollection,
        ...additionalArcTemplates.map(expect.objectContaining)
      );
      expect(comp.arcTemplatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const arcMetadata: IArcMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 40261 };
      arcMetadata.projectMetadata = projectMetadata;
      const arcTemplate: IArcTemplate = { id: 55154 };
      arcMetadata.arcTemplate = arcTemplate;

      activatedRoute.data = of({ arcMetadata });
      comp.ngOnInit();

      expect(comp.projectMetadataCollection).toContain(projectMetadata);
      expect(comp.arcTemplatesSharedCollection).toContain(arcTemplate);
      expect(comp.arcMetadata).toEqual(arcMetadata);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcMetadata>>();
      const arcMetadata = { id: 123 };
      jest.spyOn(arcMetadataFormService, 'getArcMetadata').mockReturnValue(arcMetadata);
      jest.spyOn(arcMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arcMetadata }));
      saveSubject.complete();

      // THEN
      expect(arcMetadataFormService.getArcMetadata).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(arcMetadataService.update).toHaveBeenCalledWith(expect.objectContaining(arcMetadata));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcMetadata>>();
      const arcMetadata = { id: 123 };
      jest.spyOn(arcMetadataFormService, 'getArcMetadata').mockReturnValue({ id: null });
      jest.spyOn(arcMetadataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcMetadata: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arcMetadata }));
      saveSubject.complete();

      // THEN
      expect(arcMetadataFormService.getArcMetadata).toHaveBeenCalled();
      expect(arcMetadataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArcMetadata>>();
      const arcMetadata = { id: 123 };
      jest.spyOn(arcMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arcMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(arcMetadataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProjectMetadata', () => {
      it('Should forward to projectMetadataService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectMetadataService, 'compareProjectMetadata');
        comp.compareProjectMetadata(entity, entity2);
        expect(projectMetadataService.compareProjectMetadata).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareArcTemplate', () => {
      it('Should forward to arcTemplateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(arcTemplateService, 'compareArcTemplate');
        comp.compareArcTemplate(entity, entity2);
        expect(arcTemplateService.compareArcTemplate).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
