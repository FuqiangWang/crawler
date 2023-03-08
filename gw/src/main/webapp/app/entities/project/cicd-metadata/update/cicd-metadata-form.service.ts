import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICicdMetadata, NewCicdMetadata } from '../cicd-metadata.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICicdMetadata for edit and NewCicdMetadataFormGroupInput for create.
 */
type CicdMetadataFormGroupInput = ICicdMetadata | PartialWithRequiredKeyOf<NewCicdMetadata>;

type CicdMetadataFormDefaults = Pick<NewCicdMetadata, 'id'>;

type CicdMetadataFormGroupContent = {
  id: FormControl<ICicdMetadata['id'] | NewCicdMetadata['id']>;
  repositoryType: FormControl<ICicdMetadata['repositoryType']>;
  repositoryName: FormControl<ICicdMetadata['repositoryName']>;
  ciName: FormControl<ICicdMetadata['ciName']>;
  ciUrl: FormControl<ICicdMetadata['ciUrl']>;
  buildPkg: FormControl<ICicdMetadata['buildPkg']>;
  mirrorRepository: FormControl<ICicdMetadata['mirrorRepository']>;
  cdType: FormControl<ICicdMetadata['cdType']>;
  autoIp: FormControl<ICicdMetadata['autoIp']>;
  autoPort: FormControl<ICicdMetadata['autoPort']>;
  autoUser: FormControl<ICicdMetadata['autoUser']>;
  autoPwd: FormControl<ICicdMetadata['autoPwd']>;
  autoKey: FormControl<ICicdMetadata['autoKey']>;
  deploy: FormControl<ICicdMetadata['deploy']>;
  rentId: FormControl<ICicdMetadata['rentId']>;
  updateTime: FormControl<ICicdMetadata['updateTime']>;
  createTime: FormControl<ICicdMetadata['createTime']>;
  projectMetadata: FormControl<ICicdMetadata['projectMetadata']>;
};

export type CicdMetadataFormGroup = FormGroup<CicdMetadataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CicdMetadataFormService {
  createCicdMetadataFormGroup(cicdMetadata: CicdMetadataFormGroupInput = { id: null }): CicdMetadataFormGroup {
    const cicdMetadataRawValue = {
      ...this.getFormDefaults(),
      ...cicdMetadata,
    };
    return new FormGroup<CicdMetadataFormGroupContent>({
      id: new FormControl(
        { value: cicdMetadataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      repositoryType: new FormControl(cicdMetadataRawValue.repositoryType, {
        validators: [Validators.required],
      }),
      repositoryName: new FormControl(cicdMetadataRawValue.repositoryName, {
        validators: [Validators.required],
      }),
      ciName: new FormControl(cicdMetadataRawValue.ciName, {
        validators: [Validators.required],
      }),
      ciUrl: new FormControl(cicdMetadataRawValue.ciUrl, {
        validators: [Validators.required],
      }),
      buildPkg: new FormControl(cicdMetadataRawValue.buildPkg, {
        validators: [Validators.required],
      }),
      mirrorRepository: new FormControl(cicdMetadataRawValue.mirrorRepository),
      cdType: new FormControl(cicdMetadataRawValue.cdType, {
        validators: [Validators.required],
      }),
      autoIp: new FormControl(cicdMetadataRawValue.autoIp),
      autoPort: new FormControl(cicdMetadataRawValue.autoPort),
      autoUser: new FormControl(cicdMetadataRawValue.autoUser),
      autoPwd: new FormControl(cicdMetadataRawValue.autoPwd),
      autoKey: new FormControl(cicdMetadataRawValue.autoKey),
      deploy: new FormControl(cicdMetadataRawValue.deploy),
      rentId: new FormControl(cicdMetadataRawValue.rentId, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(cicdMetadataRawValue.updateTime, {
        validators: [Validators.required],
      }),
      createTime: new FormControl(cicdMetadataRawValue.createTime, {
        validators: [Validators.required],
      }),
      projectMetadata: new FormControl(cicdMetadataRawValue.projectMetadata),
    });
  }

  getCicdMetadata(form: CicdMetadataFormGroup): ICicdMetadata | NewCicdMetadata {
    return form.getRawValue() as ICicdMetadata | NewCicdMetadata;
  }

  resetForm(form: CicdMetadataFormGroup, cicdMetadata: CicdMetadataFormGroupInput): void {
    const cicdMetadataRawValue = { ...this.getFormDefaults(), ...cicdMetadata };
    form.reset(
      {
        ...cicdMetadataRawValue,
        id: { value: cicdMetadataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CicdMetadataFormDefaults {
    return {
      id: null,
    };
  }
}
