import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project-metadata.test-samples';

import { ProjectMetadataFormService } from './project-metadata-form.service';

describe('ProjectMetadata Form Service', () => {
  let service: ProjectMetadataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectMetadataFormService);
  });

  describe('Service methods', () => {
    describe('createProjectMetadataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectMetadataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            language: expect.any(Object),
            langVersion: expect.any(Object),
            buildTool: expect.any(Object),
            buildToolVersion: expect.any(Object),
            banner: expect.any(Object),
            favicon: expect.any(Object),
            version: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
          })
        );
      });

      it('passing IProjectMetadata should create a new form with FormGroup', () => {
        const formGroup = service.createProjectMetadataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            language: expect.any(Object),
            langVersion: expect.any(Object),
            buildTool: expect.any(Object),
            buildToolVersion: expect.any(Object),
            banner: expect.any(Object),
            favicon: expect.any(Object),
            version: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
          })
        );
      });
    });

    describe('getProjectMetadata', () => {
      it('should return NewProjectMetadata for default ProjectMetadata initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectMetadataFormGroup(sampleWithNewData);

        const projectMetadata = service.getProjectMetadata(formGroup) as any;

        expect(projectMetadata).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjectMetadata for empty ProjectMetadata initial value', () => {
        const formGroup = service.createProjectMetadataFormGroup();

        const projectMetadata = service.getProjectMetadata(formGroup) as any;

        expect(projectMetadata).toMatchObject({});
      });

      it('should return IProjectMetadata', () => {
        const formGroup = service.createProjectMetadataFormGroup(sampleWithRequiredData);

        const projectMetadata = service.getProjectMetadata(formGroup) as any;

        expect(projectMetadata).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjectMetadata should not enable id FormControl', () => {
        const formGroup = service.createProjectMetadataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjectMetadata should disable id FormControl', () => {
        const formGroup = service.createProjectMetadataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
