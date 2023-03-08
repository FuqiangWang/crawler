import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cicd-metadata.test-samples';

import { CicdMetadataFormService } from './cicd-metadata-form.service';

describe('CicdMetadata Form Service', () => {
  let service: CicdMetadataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CicdMetadataFormService);
  });

  describe('Service methods', () => {
    describe('createCicdMetadataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCicdMetadataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            repositoryType: expect.any(Object),
            repositoryName: expect.any(Object),
            ciName: expect.any(Object),
            ciUrl: expect.any(Object),
            buildPkg: expect.any(Object),
            mirrorRepository: expect.any(Object),
            cdType: expect.any(Object),
            autoIp: expect.any(Object),
            autoPort: expect.any(Object),
            autoUser: expect.any(Object),
            autoPwd: expect.any(Object),
            autoKey: expect.any(Object),
            deploy: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
            projectMetadata: expect.any(Object),
          })
        );
      });

      it('passing ICicdMetadata should create a new form with FormGroup', () => {
        const formGroup = service.createCicdMetadataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            repositoryType: expect.any(Object),
            repositoryName: expect.any(Object),
            ciName: expect.any(Object),
            ciUrl: expect.any(Object),
            buildPkg: expect.any(Object),
            mirrorRepository: expect.any(Object),
            cdType: expect.any(Object),
            autoIp: expect.any(Object),
            autoPort: expect.any(Object),
            autoUser: expect.any(Object),
            autoPwd: expect.any(Object),
            autoKey: expect.any(Object),
            deploy: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
            projectMetadata: expect.any(Object),
          })
        );
      });
    });

    describe('getCicdMetadata', () => {
      it('should return NewCicdMetadata for default CicdMetadata initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCicdMetadataFormGroup(sampleWithNewData);

        const cicdMetadata = service.getCicdMetadata(formGroup) as any;

        expect(cicdMetadata).toMatchObject(sampleWithNewData);
      });

      it('should return NewCicdMetadata for empty CicdMetadata initial value', () => {
        const formGroup = service.createCicdMetadataFormGroup();

        const cicdMetadata = service.getCicdMetadata(formGroup) as any;

        expect(cicdMetadata).toMatchObject({});
      });

      it('should return ICicdMetadata', () => {
        const formGroup = service.createCicdMetadataFormGroup(sampleWithRequiredData);

        const cicdMetadata = service.getCicdMetadata(formGroup) as any;

        expect(cicdMetadata).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICicdMetadata should not enable id FormControl', () => {
        const formGroup = service.createCicdMetadataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCicdMetadata should disable id FormControl', () => {
        const formGroup = service.createCicdMetadataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
