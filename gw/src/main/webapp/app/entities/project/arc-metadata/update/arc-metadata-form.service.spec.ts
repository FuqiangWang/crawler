import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../arc-metadata.test-samples';

import { ArcMetadataFormService } from './arc-metadata-form.service';

describe('ArcMetadata Form Service', () => {
  let service: ArcMetadataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArcMetadataFormService);
  });

  describe('Service methods', () => {
    describe('createArcMetadataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArcMetadataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            packageName: expect.any(Object),
            serviceDiscoveryType: expect.any(Object),
            devDatabaseType: expect.any(Object),
            prodDatabaseType: expect.any(Object),
            cacheProvider: expect.any(Object),
            messageBroker: expect.any(Object),
            serverPort: expect.any(Object),
            frontMessage: expect.any(Object),
            clientFramework: expect.any(Object),
            languages: expect.any(Object),
            testFramework: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
            projectMetadata: expect.any(Object),
            arcTemplate: expect.any(Object),
          })
        );
      });

      it('passing IArcMetadata should create a new form with FormGroup', () => {
        const formGroup = service.createArcMetadataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            packageName: expect.any(Object),
            serviceDiscoveryType: expect.any(Object),
            devDatabaseType: expect.any(Object),
            prodDatabaseType: expect.any(Object),
            cacheProvider: expect.any(Object),
            messageBroker: expect.any(Object),
            serverPort: expect.any(Object),
            frontMessage: expect.any(Object),
            clientFramework: expect.any(Object),
            languages: expect.any(Object),
            testFramework: expect.any(Object),
            rentId: expect.any(Object),
            updateTime: expect.any(Object),
            createTime: expect.any(Object),
            projectMetadata: expect.any(Object),
            arcTemplate: expect.any(Object),
          })
        );
      });
    });

    describe('getArcMetadata', () => {
      it('should return NewArcMetadata for default ArcMetadata initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createArcMetadataFormGroup(sampleWithNewData);

        const arcMetadata = service.getArcMetadata(formGroup) as any;

        expect(arcMetadata).toMatchObject(sampleWithNewData);
      });

      it('should return NewArcMetadata for empty ArcMetadata initial value', () => {
        const formGroup = service.createArcMetadataFormGroup();

        const arcMetadata = service.getArcMetadata(formGroup) as any;

        expect(arcMetadata).toMatchObject({});
      });

      it('should return IArcMetadata', () => {
        const formGroup = service.createArcMetadataFormGroup(sampleWithRequiredData);

        const arcMetadata = service.getArcMetadata(formGroup) as any;

        expect(arcMetadata).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArcMetadata should not enable id FormControl', () => {
        const formGroup = service.createArcMetadataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArcMetadata should disable id FormControl', () => {
        const formGroup = service.createArcMetadataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
