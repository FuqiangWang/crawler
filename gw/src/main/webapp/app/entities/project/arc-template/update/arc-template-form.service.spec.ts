import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../arc-template.test-samples';

import { ArcTemplateFormService } from './arc-template-form.service';

describe('ArcTemplate Form Service', () => {
  let service: ArcTemplateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArcTemplateFormService);
  });

  describe('Service methods', () => {
    describe('createArcTemplateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArcTemplateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
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
          })
        );
      });

      it('passing IArcTemplate should create a new form with FormGroup', () => {
        const formGroup = service.createArcTemplateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
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
          })
        );
      });
    });

    describe('getArcTemplate', () => {
      it('should return NewArcTemplate for default ArcTemplate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createArcTemplateFormGroup(sampleWithNewData);

        const arcTemplate = service.getArcTemplate(formGroup) as any;

        expect(arcTemplate).toMatchObject(sampleWithNewData);
      });

      it('should return NewArcTemplate for empty ArcTemplate initial value', () => {
        const formGroup = service.createArcTemplateFormGroup();

        const arcTemplate = service.getArcTemplate(formGroup) as any;

        expect(arcTemplate).toMatchObject({});
      });

      it('should return IArcTemplate', () => {
        const formGroup = service.createArcTemplateFormGroup(sampleWithRequiredData);

        const arcTemplate = service.getArcTemplate(formGroup) as any;

        expect(arcTemplate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArcTemplate should not enable id FormControl', () => {
        const formGroup = service.createArcTemplateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArcTemplate should disable id FormControl', () => {
        const formGroup = service.createArcTemplateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
