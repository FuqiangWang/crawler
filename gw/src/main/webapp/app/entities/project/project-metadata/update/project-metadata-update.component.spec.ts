import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectMetadataFormService } from './project-metadata-form.service';
import { ProjectMetadataService } from '../service/project-metadata.service';
import { IProjectMetadata } from '../project-metadata.model';

import { ProjectMetadataUpdateComponent } from './project-metadata-update.component';

describe('ProjectMetadata Management Update Component', () => {
  let comp: ProjectMetadataUpdateComponent;
  let fixture: ComponentFixture<ProjectMetadataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectMetadataFormService: ProjectMetadataFormService;
  let projectMetadataService: ProjectMetadataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectMetadataUpdateComponent],
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
      .overrideTemplate(ProjectMetadataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectMetadataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectMetadataFormService = TestBed.inject(ProjectMetadataFormService);
    projectMetadataService = TestBed.inject(ProjectMetadataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projectMetadata: IProjectMetadata = { id: 456 };

      activatedRoute.data = of({ projectMetadata });
      comp.ngOnInit();

      expect(comp.projectMetadata).toEqual(projectMetadata);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectMetadata>>();
      const projectMetadata = { id: 123 };
      jest.spyOn(projectMetadataFormService, 'getProjectMetadata').mockReturnValue(projectMetadata);
      jest.spyOn(projectMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectMetadata }));
      saveSubject.complete();

      // THEN
      expect(projectMetadataFormService.getProjectMetadata).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectMetadataService.update).toHaveBeenCalledWith(expect.objectContaining(projectMetadata));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectMetadata>>();
      const projectMetadata = { id: 123 };
      jest.spyOn(projectMetadataFormService, 'getProjectMetadata').mockReturnValue({ id: null });
      jest.spyOn(projectMetadataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectMetadata: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectMetadata }));
      saveSubject.complete();

      // THEN
      expect(projectMetadataFormService.getProjectMetadata).toHaveBeenCalled();
      expect(projectMetadataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectMetadata>>();
      const projectMetadata = { id: 123 };
      jest.spyOn(projectMetadataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectMetadata });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectMetadataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
