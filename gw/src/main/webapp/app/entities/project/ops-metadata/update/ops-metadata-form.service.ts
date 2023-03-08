import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOpsMetadata, NewOpsMetadata } from '../ops-metadata.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOpsMetadata for edit and NewOpsMetadataFormGroupInput for create.
 */
type OpsMetadataFormGroupInput = IOpsMetadata | PartialWithRequiredKeyOf<NewOpsMetadata>;

type OpsMetadataFormDefaults = Pick<NewOpsMetadata, 'id'>;

type OpsMetadataFormGroupContent = {
  id: FormControl<IOpsMetadata['id'] | NewOpsMetadata['id']>;
  opsSystem: FormControl<IOpsMetadata['opsSystem']>;
  rentId: FormControl<IOpsMetadata['rentId']>;
  createTime: FormControl<IOpsMetadata['createTime']>;
  updateTime: FormControl<IOpsMetadata['updateTime']>;
  projectMetadata: FormControl<IOpsMetadata['projectMetadata']>;
};

export type OpsMetadataFormGroup = FormGroup<OpsMetadataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OpsMetadataFormService {
  createOpsMetadataFormGroup(opsMetadata: OpsMetadataFormGroupInput = { id: null }): OpsMetadataFormGroup {
    const opsMetadataRawValue = {
      ...this.getFormDefaults(),
      ...opsMetadata,
    };
    return new FormGroup<OpsMetadataFormGroupContent>({
      id: new FormControl(
        { value: opsMetadataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      opsSystem: new FormControl(opsMetadataRawValue.opsSystem, {
        validators: [Validators.required],
      }),
      rentId: new FormControl(opsMetadataRawValue.rentId),
      createTime: new FormControl(opsMetadataRawValue.createTime, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(opsMetadataRawValue.updateTime, {
        validators: [Validators.required],
      }),
      projectMetadata: new FormControl(opsMetadataRawValue.projectMetadata),
    });
  }

  getOpsMetadata(form: OpsMetadataFormGroup): IOpsMetadata | NewOpsMetadata {
    return form.getRawValue() as IOpsMetadata | NewOpsMetadata;
  }

  resetForm(form: OpsMetadataFormGroup, opsMetadata: OpsMetadataFormGroupInput): void {
    const opsMetadataRawValue = { ...this.getFormDefaults(), ...opsMetadata };
    form.reset(
      {
        ...opsMetadataRawValue,
        id: { value: opsMetadataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OpsMetadataFormDefaults {
    return {
      id: null,
    };
  }
}
