import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OpsMetadataFormService } from './ops-metadata-form.service';
import { OpsMetadataService } from '../service/ops-metadata.service';
import { IOpsMetadata } from '../ops-metadata.model';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';

import { OpsMetadataUpdateComponent } from './ops-metadata-update.component';

describe('OpsMetadata Management Update Component', () => {
  let comp: OpsMetadataUpdateComponent;
  let fixture: ComponentFixture<OpsMetadataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let opsMetadataFormService: OpsMetadataFormService;
  let opsMetadataService: OpsMetadataService;
  let projectMetadataService: ProjectMetadataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OpsMetadataUpdateComponent],
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
      .overrideTemplate(OpsMetadataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OpsMetadataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    opsMetadataFormService = TestBed.inject(OpsMetadataFormService);
    opsMetadataService = TestBed.inject(OpsMetadataService);
    projectMetadataService = TestBed.inject(ProjectMetadataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call projectMetadata query and add missing value', () => {
      const opsMetadata: IOpsMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 63357 };
      opsMetadata.projectMetadata = projectMetadata;

      const projectMetadataCollection: IProjectMetadata[] = [{ id: 20215 }];
      jest.spyOn(projectMetadataService, 'query').mockReturnValue(of(new HttpResponse({ body: projectMetadataCollection })));
      const expectedCollection: IProjectMetadata[] = [projectMetadata, ...projectMetadataCollection];
      jest.spyOn(projectMetadataService, 'addProjectMetadataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ opsMetadata });
      comp.ngOnInit();

      expect(projectMetadataService.query).toHaveBeenCalled();
      expect(projectMetadataService.addProjectMetadataToCollectionIfMissing).toHaveBeenCalledWith(
        projectMetadataCollection,
        projectMetadata
      );
      expect(comp.projectMetadataCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const opsMetadata: IOpsMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 14299 };
      opsMetadata.projectMetadata = projectMetadata;

      activatedRoute.data = of({ opsMetadata });
      comp.ngOnInit();

      expect(comp.projectMetadataCollection).toContain(projectMetadata);
      expect(comp.opsMetadata).toEqual(opsMetadata);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpsMetadata>>();
      const opsMetadata = { id: 123 };
      jest.spyOn(opsMetadataFormService, 'getOpsMetadata').mockReturnValue(opsMetadata);
      jest.spyOn(opsMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opsMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opsMetadata }));
      saveSubject.complete();

      // THEN
      expect(opsMetadataFormService.getOpsMetadata).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(opsMetadataService.update).toHaveBeenCalledWith(expect.objectContaining(opsMetadata));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpsMetadata>>();
      const opsMetadata = { id: 123 };
      jest.spyOn(opsMetadataFormService, 'getOpsMetadata').mockReturnValue({ id: null });
      jest.spyOn(opsMetadataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opsMetadata: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: opsMetadata }));
      saveSubject.complete();

      // THEN
      expect(opsMetadataFormService.getOpsMetadata).toHaveBeenCalled();
      expect(opsMetadataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOpsMetadata>>();
      const opsMetadata = { id: 123 };
      jest.spyOn(opsMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ opsMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(opsMetadataService.update).toHaveBeenCalled();
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
  });
});
