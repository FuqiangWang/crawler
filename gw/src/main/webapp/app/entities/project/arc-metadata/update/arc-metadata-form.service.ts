import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArcMetadata, NewArcMetadata } from '../arc-metadata.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArcMetadata for edit and NewArcMetadataFormGroupInput for create.
 */
type ArcMetadataFormGroupInput = IArcMetadata | PartialWithRequiredKeyOf<NewArcMetadata>;

type ArcMetadataFormDefaults = Pick<NewArcMetadata, 'id'>;

type ArcMetadataFormGroupContent = {
  id: FormControl<IArcMetadata['id'] | NewArcMetadata['id']>;
  type: FormControl<IArcMetadata['type']>;
  packageName: FormControl<IArcMetadata['packageName']>;
  serviceDiscoveryType: FormControl<IArcMetadata['serviceDiscoveryType']>;
  devDatabaseType: FormControl<IArcMetadata['devDatabaseType']>;
  prodDatabaseType: FormControl<IArcMetadata['prodDatabaseType']>;
  cacheProvider: FormControl<IArcMetadata['cacheProvider']>;
  messageBroker: FormControl<IArcMetadata['messageBroker']>;
  serverPort: FormControl<IArcMetadata['serverPort']>;
  frontMessage: FormControl<IArcMetadata['frontMessage']>;
  clientFramework: FormControl<IArcMetadata['clientFramework']>;
  languages: FormControl<IArcMetadata['languages']>;
  testFramework: FormControl<IArcMetadata['testFramework']>;
  rentId: FormControl<IArcMetadata['rentId']>;
  updateTime: FormControl<IArcMetadata['updateTime']>;
  createTime: FormControl<IArcMetadata['createTime']>;
  projectMetadata: FormControl<IArcMetadata['projectMetadata']>;
  arcTemplate: FormControl<IArcMetadata['arcTemplate']>;
};

export type ArcMetadataFormGroup = FormGroup<ArcMetadataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArcMetadataFormService {
  createArcMetadataFormGroup(arcMetadata: ArcMetadataFormGroupInput = { id: null }): ArcMetadataFormGroup {
    const arcMetadataRawValue = {
      ...this.getFormDefaults(),
      ...arcMetadata,
    };
    return new FormGroup<ArcMetadataFormGroupContent>({
      id: new FormControl(
        { value: arcMetadataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(arcMetadataRawValue.type, {
        validators: [Validators.required],
      }),
      packageName: new FormControl(arcMetadataRawValue.packageName, {
        validators: [Validators.required],
      }),
      serviceDiscoveryType: new FormControl(arcMetadataRawValue.serviceDiscoveryType),
      devDatabaseType: new FormControl(arcMetadataRawValue.devDatabaseType),
      prodDatabaseType: new FormControl(arcMetadataRawValue.prodDatabaseType),
      cacheProvider: new FormControl(arcMetadataRawValue.cacheProvider),
      messageBroker: new FormControl(arcMetadataRawValue.messageBroker),
      serverPort: new FormControl(arcMetadataRawValue.serverPort, {
        validators: [Validators.required],
      }),
      frontMessage: new FormControl(arcMetadataRawValue.frontMessage),
      clientFramework: new FormControl(arcMetadataRawValue.clientFramework),
      languages: new FormControl(arcMetadataRawValue.languages),
      testFramework: new FormControl(arcMetadataRawValue.testFramework),
      rentId: new FormControl(arcMetadataRawValue.rentId, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(arcMetadataRawValue.updateTime, {
        validators: [Validators.required],
      }),
      createTime: new FormControl(arcMetadataRawValue.createTime, {
        validators: [Validators.required],
      }),
      projectMetadata: new FormControl(arcMetadataRawValue.projectMetadata),
      arcTemplate: new FormControl(arcMetadataRawValue.arcTemplate),
    });
  }

  getArcMetadata(form: ArcMetadataFormGroup): IArcMetadata | NewArcMetadata {
    return form.getRawValue() as IArcMetadata | NewArcMetadata;
  }

  resetForm(form: ArcMetadataFormGroup, arcMetadata: ArcMetadataFormGroupInput): void {
    const arcMetadataRawValue = { ...this.getFormDefaults(), ...arcMetadata };
    form.reset(
      {
        ...arcMetadataRawValue,
        id: { value: arcMetadataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArcMetadataFormDefaults {
    return {
      id: null,
    };
  }
}
