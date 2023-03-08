import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAccountConfig, NewAccountConfig } from '../account-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccountConfig for edit and NewAccountConfigFormGroupInput for create.
 */
type AccountConfigFormGroupInput = IAccountConfig | PartialWithRequiredKeyOf<NewAccountConfig>;

type AccountConfigFormDefaults = Pick<NewAccountConfig, 'id'>;

type AccountConfigFormGroupContent = {
  id: FormControl<IAccountConfig['id'] | NewAccountConfig['id']>;
  gitHubUser: FormControl<IAccountConfig['gitHubUser']>;
  gitHubPwd: FormControl<IAccountConfig['gitHubPwd']>;
  gitLabUser: FormControl<IAccountConfig['gitLabUser']>;
  gitLabPwd: FormControl<IAccountConfig['gitLabPwd']>;
  giteeUser: FormControl<IAccountConfig['giteeUser']>;
  giteePwd: FormControl<IAccountConfig['giteePwd']>;
  dockerHubUser: FormControl<IAccountConfig['dockerHubUser']>;
  dockHubPwd: FormControl<IAccountConfig['dockHubPwd']>;
  rendId: FormControl<IAccountConfig['rendId']>;
};

export type AccountConfigFormGroup = FormGroup<AccountConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccountConfigFormService {
  createAccountConfigFormGroup(accountConfig: AccountConfigFormGroupInput = { id: null }): AccountConfigFormGroup {
    const accountConfigRawValue = {
      ...this.getFormDefaults(),
      ...accountConfig,
    };
    return new FormGroup<AccountConfigFormGroupContent>({
      id: new FormControl(
        { value: accountConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      gitHubUser: new FormControl(accountConfigRawValue.gitHubUser),
      gitHubPwd: new FormControl(accountConfigRawValue.gitHubPwd),
      gitLabUser: new FormControl(accountConfigRawValue.gitLabUser),
      gitLabPwd: new FormControl(accountConfigRawValue.gitLabPwd),
      giteeUser: new FormControl(accountConfigRawValue.giteeUser),
      giteePwd: new FormControl(accountConfigRawValue.giteePwd),
      dockerHubUser: new FormControl(accountConfigRawValue.dockerHubUser),
      dockHubPwd: new FormControl(accountConfigRawValue.dockHubPwd),
      rendId: new FormControl(accountConfigRawValue.rendId, {
        validators: [Validators.required],
      }),
    });
  }

  getAccountConfig(form: AccountConfigFormGroup): IAccountConfig | NewAccountConfig {
    return form.getRawValue() as IAccountConfig | NewAccountConfig;
  }

  resetForm(form: AccountConfigFormGroup, accountConfig: AccountConfigFormGroupInput): void {
    const accountConfigRawValue = { ...this.getFormDefaults(), ...accountConfig };
    form.reset(
      {
        ...accountConfigRawValue,
        id: { value: accountConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccountConfigFormDefaults {
    return {
      id: null,
    };
  }
}
