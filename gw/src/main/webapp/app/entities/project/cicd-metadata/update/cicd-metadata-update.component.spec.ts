import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CicdMetadataFormService } from './cicd-metadata-form.service';
import { CicdMetadataService } from '../service/cicd-metadata.service';
import { ICicdMetadata } from '../cicd-metadata.model';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';

import { CicdMetadataUpdateComponent } from './cicd-metadata-update.component';

describe('CicdMetadata Management Update Component', () => {
  let comp: CicdMetadataUpdateComponent;
  let fixture: ComponentFixture<CicdMetadataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cicdMetadataFormService: CicdMetadataFormService;
  let cicdMetadataService: CicdMetadataService;
  let projectMetadataService: ProjectMetadataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CicdMetadataUpdateComponent],
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
      .overrideTemplate(CicdMetadataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CicdMetadataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cicdMetadataFormService = TestBed.inject(CicdMetadataFormService);
    cicdMetadataService = TestBed.inject(CicdMetadataService);
    projectMetadataService = TestBed.inject(ProjectMetadataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call projectMetadata query and add missing value', () => {
      const cicdMetadata: ICicdMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 78477 };
      cicdMetadata.projectMetadata = projectMetadata;

      const projectMetadataCollection: IProjectMetadata[] = [{ id: 33917 }];
      jest.spyOn(projectMetadataService, 'query').mockReturnValue(of(new HttpResponse({ body: projectMetadataCollection })));
      const expectedCollection: IProjectMetadata[] = [projectMetadata, ...projectMetadataCollection];
      jest.spyOn(projectMetadataService, 'addProjectMetadataToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cicdMetadata });
      comp.ngOnInit();

      expect(projectMetadataService.query).toHaveBeenCalled();
      expect(projectMetadataService.addProjectMetadataToCollectionIfMissing).toHaveBeenCalledWith(
        projectMetadataCollection,
        projectMetadata
      );
      expect(comp.projectMetadataCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cicdMetadata: ICicdMetadata = { id: 456 };
      const projectMetadata: IProjectMetadata = { id: 74123 };
      cicdMetadata.projectMetadata = projectMetadata;

      activatedRoute.data = of({ cicdMetadata });
      comp.ngOnInit();

      expect(comp.projectMetadataCollection).toContain(projectMetadata);
      expect(comp.cicdMetadata).toEqual(cicdMetadata);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICicdMetadata>>();
      const cicdMetadata = { id: 123 };
      jest.spyOn(cicdMetadataFormService, 'getCicdMetadata').mockReturnValue(cicdMetadata);
      jest.spyOn(cicdMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cicdMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cicdMetadata }));
      saveSubject.complete();

      // THEN
      expect(cicdMetadataFormService.getCicdMetadata).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cicdMetadataService.update).toHaveBeenCalledWith(expect.objectContaining(cicdMetadata));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICicdMetadata>>();
      const cicdMetadata = { id: 123 };
      jest.spyOn(cicdMetadataFormService, 'getCicdMetadata').mockReturnValue({ id: null });
      jest.spyOn(cicdMetadataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cicdMetadata: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cicdMetadata }));
      saveSubject.complete();

      // THEN
      expect(cicdMetadataFormService.getCicdMetadata).toHaveBeenCalled();
      expect(cicdMetadataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICicdMetadata>>();
      const cicdMetadata = { id: 123 };
      jest.spyOn(cicdMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cicdMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cicdMetadataService.update).toHaveBeenCalled();
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
