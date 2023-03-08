import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../account-config.test-samples';

import { AccountConfigFormService } from './account-config-form.service';

describe('AccountConfig Form Service', () => {
  let service: AccountConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountConfigFormService);
  });

  describe('Service methods', () => {
    describe('createAccountConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccountConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gitHubUser: expect.any(Object),
            gitHubPwd: expect.any(Object),
            gitLabUser: expect.any(Object),
            gitLabPwd: expect.any(Object),
            giteeUser: expect.any(Object),
            giteePwd: expect.any(Object),
            dockerHubUser: expect.any(Object),
            dockHubPwd: expect.any(Object),
            rendId: expect.any(Object),
          })
        );
      });

      it('passing IAccountConfig should create a new form with FormGroup', () => {
        const formGroup = service.createAccountConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            gitHubUser: expect.any(Object),
            gitHubPwd: expect.any(Object),
            gitLabUser: expect.any(Object),
            gitLabPwd: expect.any(Object),
            giteeUser: expect.any(Object),
            giteePwd: expect.any(Object),
            dockerHubUser: expect.any(Object),
            dockHubPwd: expect.any(Object),
            rendId: expect.any(Object),
          })
        );
      });
    });

    describe('getAccountConfig', () => {
      it('should return NewAccountConfig for default AccountConfig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccountConfigFormGroup(sampleWithNewData);

        const accountConfig = service.getAccountConfig(formGroup) as any;

        expect(accountConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccountConfig for empty AccountConfig initial value', () => {
        const formGroup = service.createAccountConfigFormGroup();

        const accountConfig = service.getAccountConfig(formGroup) as any;

        expect(accountConfig).toMatchObject({});
      });

      it('should return IAccountConfig', () => {
        const formGroup = service.createAccountConfigFormGroup(sampleWithRequiredData);

        const accountConfig = service.getAccountConfig(formGroup) as any;

        expect(accountConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccountConfig should not enable id FormControl', () => {
        const formGroup = service.createAccountConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAccountConfig should disable id FormControl', () => {
        const formGroup = service.createAccountConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
