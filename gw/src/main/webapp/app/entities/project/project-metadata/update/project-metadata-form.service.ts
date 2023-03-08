import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjectMetadata, NewProjectMetadata } from '../project-metadata.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjectMetadata for edit and NewProjectMetadataFormGroupInput for create.
 */
type ProjectMetadataFormGroupInput = IProjectMetadata | PartialWithRequiredKeyOf<NewProjectMetadata>;

type ProjectMetadataFormDefaults = Pick<NewProjectMetadata, 'id'>;

type ProjectMetadataFormGroupContent = {
  id: FormControl<IProjectMetadata['id'] | NewProjectMetadata['id']>;
  name: FormControl<IProjectMetadata['name']>;
  type: FormControl<IProjectMetadata['type']>;
  language: FormControl<IProjectMetadata['language']>;
  langVersion: FormControl<IProjectMetadata['langVersion']>;
  buildTool: FormControl<IProjectMetadata['buildTool']>;
  buildToolVersion: FormControl<IProjectMetadata['buildToolVersion']>;
  banner: FormControl<IProjectMetadata['banner']>;
  favicon: FormControl<IProjectMetadata['favicon']>;
  version: FormControl<IProjectMetadata['version']>;
  rentId: FormControl<IProjectMetadata['rentId']>;
  updateTime: FormControl<IProjectMetadata['updateTime']>;
  createTime: FormControl<IProjectMetadata['createTime']>;
};

export type ProjectMetadataFormGroup = FormGroup<ProjectMetadataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectMetadataFormService {
  createProjectMetadataFormGroup(projectMetadata: ProjectMetadataFormGroupInput = { id: null }): ProjectMetadataFormGroup {
    const projectMetadataRawValue = {
      ...this.getFormDefaults(),
      ...projectMetadata,
    };
    return new FormGroup<ProjectMetadataFormGroupContent>({
      id: new FormControl(
        { value: projectMetadataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(projectMetadataRawValue.name, {
        validators: [Validators.required],
      }),
      type: new FormControl(projectMetadataRawValue.type, {
        validators: [Validators.required],
      }),
      language: new FormControl(projectMetadataRawValue.language, {
        validators: [Validators.required],
      }),
      langVersion: new FormControl(projectMetadataRawValue.langVersion),
      buildTool: new FormControl(projectMetadataRawValue.buildTool),
      buildToolVersion: new FormControl(projectMetadataRawValue.buildToolVersion),
      banner: new FormControl(projectMetadataRawValue.banner, {
        validators: [Validators.required],
      }),
      favicon: new FormControl(projectMetadataRawValue.favicon, {
        validators: [Validators.required],
      }),
      version: new FormControl(projectMetadataRawValue.version, {
        validators: [Validators.required],
      }),
      rentId: new FormControl(projectMetadataRawValue.rentId, {
        validators: [Validators.required],
      }),
      updateTime: new FormControl(projectMetadataRawValue.updateTime, {
        validators: [Validators.required],
      }),
      createTime: new FormControl(projectMetadataRawValue.createTime, {
        validators: [Validators.required],
      }),
    });
  }

  getProjectMetadata(form: ProjectMetadataFormGroup): IProjectMetadata | NewProjectMetadata {
    return form.getRawValue() as IProjectMetadata | NewProjectMetadata;
  }

  resetForm(form: ProjectMetadataFormGroup, projectMetadata: ProjectMetadataFormGroupInput): void {
    const projectMetadataRawValue = { ...this.getFormDefaults(), ...projectMetadata };
    form.reset(
      {
        ...projectMetadataRawValue,
        id: { value: projectMetadataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectMetadataFormDefaults {
    return {
      id: null,
    };
  }
}
