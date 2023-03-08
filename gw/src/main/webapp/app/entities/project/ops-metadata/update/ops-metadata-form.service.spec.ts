import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ops-metadata.test-samples';

import { OpsMetadataFormService } from './ops-metadata-form.service';

describe('OpsMetadata Form Service', () => {
  let service: OpsMetadataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpsMetadataFormService);
  });

  describe('Service methods', () => {
    describe('createOpsMetadataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOpsMetadataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            opsSystem: expect.any(Object),
            rentId: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
            projectMetadata: expect.any(Object),
          })
        );
      });

      it('passing IOpsMetadata should create a new form with FormGroup', () => {
        const formGroup = service.createOpsMetadataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            opsSystem: expect.any(Object),
            rentId: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
            projectMetadata: expect.any(Object),
          })
        );
      });
    });

    describe('getOpsMetadata', () => {
      it('should return NewOpsMetadata for default OpsMetadata initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOpsMetadataFormGroup(sampleWithNewData);

        const opsMetadata = service.getOpsMetadata(formGroup) as any;

        expect(opsMetadata).toMatchObject(sampleWithNewData);
      });

      it('should return NewOpsMetadata for empty OpsMetadata initial value', () => {
        const formGroup = service.createOpsMetadataFormGroup();

        const opsMetadata = service.getOpsMetadata(formGroup) as any;

        expect(opsMetadata).toMatchObject({});
      });

      it('should return IOpsMetadata', () => {
        const formGroup = service.createOpsMetadataFormGroup(sampleWithRequiredData);

        const opsMetadata = service.getOpsMetadata(formGroup) as any;

        expect(opsMetadata).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOpsMetadata should not enable id FormControl', () => {
        const formGroup = service.createOpsMetadataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOpsMetadata should disable id FormControl', () => {
        const formGroup = service.createOpsMetadataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
