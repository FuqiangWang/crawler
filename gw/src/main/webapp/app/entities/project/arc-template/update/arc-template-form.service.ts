import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArcTemplate, NewArcTemplate } from '../arc-template.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArcTemplate for edit and NewArcTemplateFormGroupInput for create.
 */
type ArcTemplateFormGroupInput = IArcTemplate | PartialWithRequiredKeyOf<NewArcTemplate>;

type ArcTemplateFormDefaults = Pick<NewArcTemplate, 'id'>;

type ArcTemplateFormGroupContent = {
  id: FormControl<IArcTemplate['id'] | NewArcTemplate['id']>;
  name: FormControl<IArcTemplate['name']>;
  packageName: FormControl<IArcTemplate['packageName']>;
  serviceDiscoveryType: FormControl<IArcTemplate['serviceDiscoveryType']>;
  devDatabaseType: FormControl<IArcTemplate['devDatabaseType']>;
  prodDatabaseType: FormControl<IArcTemplate['prodDatabaseType']>;
  cacheProvider: FormControl<IArcTemplate['cacheProvider']>;
  messageBroker: FormControl<IArcTemplate['messageBroker']>;
  serverPort: FormControl<IArcTemplate['serverPort']>;
  frontMessage: FormControl<IArcTemplate['frontMessage']>;
  clientFramework: FormControl<IArcTemplate['clientFramework']>;
  languages: FormControl<IArcTemplate['languages']>;
  testFramework: FormControl<IArcTemplate['testFramework']>;
  rentId: FormControl<IArcTemplate['rentId']>;
  updateTime: FormControl<IArcTemplate['updateTime']>;
  createTime: FormControl<IArcTemplate['createTime']>;
};

export type ArcTemplateFormGroup = FormGroup<ArcTemplateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArcTemplateFormService {
  createArcTemplateFormGroup(arcTemplate: ArcTemplateFormGroupInput = { id: null }): ArcTemplateFormGroup {
    const arcTemplateRawValue = {
      ...this.getFormDefaults(),
      ...arcTemplate,
    };
    return new FormGroup<ArcTemplateFormGroupContent>({
      id: new FormControl(
        { value: arcTemplateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(arcTemplateRawValue.name, {
        validators: [Validators.required],
      }),
      packageName: new FormControl(arcTemplateRawValue.packageName, {
        validators: [Validators.required],
      }),
      serviceDiscoveryType: new FormControl(arcTemplateRawValue.serviceDiscoveryType),
      devDatabaseType: new FormControl(arcTemplateRawValue.devDatabaseType),
      prodDatabaseType: new FormControl(arcTemplateRawValue.prodDatabaseType),
      cacheProvider: new FormControl(arcTemplateRawValue.cacheProvider),
      messageBroker: new FormControl(arcTemplateRawValue.messageBroker),
      serverPort: new FormControl(arcTemplateRawValue.serverPort, {
        validators: [Validators.required],
      }),
      frontMessage: new FormControl(arcTemplateRawValue.frontMessage),
      clientFramework: new FormControl(arcTemplateRawValue.clientFramework),
      languages: new FormControl(arcTemplateRawValue.languages),
      testFramework: new FormControl(arcTemplateRawValue.testFramework),
      rentId: new FormControl(arcTemplateRawValue.rentId, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(arcTemplateRawValue.updateTime, {
        validators: [Validators.required],
      }),
      createTime: new FormControl(arcTemplateRawValue.createTime, {
        validators: [Validators.required],
      }),
    });
  }

  getArcTemplate(form: ArcTemplateFormGroup): IArcTemplate | NewArcTemplate {
    return form.getRawValue() as IArcTemplate | NewArcTemplate;
  }

  resetForm(form: ArcTemplateFormGroup, arcTemplate: ArcTemplateFormGroupInput): void {
    const arcTemplateRawValue = { ...this.getFormDefaults(), ...arcTemplate };
    form.reset(
      {
        ...arcTemplateRawValue,
        id: { value: arcTemplateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArcTemplateFormDefaults {
    return {
      id: null,
    };
  }
}
