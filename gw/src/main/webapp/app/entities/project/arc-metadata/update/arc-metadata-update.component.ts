import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ArcMetadataFormService, ArcMetadataFormGroup } from './arc-metadata-form.service';
import { IArcMetadata } from '../arc-metadata.model';
import { ArcMetadataService } from '../service/arc-metadata.service';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';
import { IArcTemplate } from 'app/entities/project/arc-template/arc-template.model';
import { ArcTemplateService } from 'app/entities/project/arc-template/service/arc-template.service';
import { ArcModelType } from 'app/entities/enumerations/arc-model-type.model';

@Component({
  selector: 'jhi-arc-metadata-update',
  templateUrl: './arc-metadata-update.component.html',
})
export class ArcMetadataUpdateComponent implements OnInit {
  isSaving = false;
  arcMetadata: IArcMetadata | null = null;
  arcModelTypeValues = Object.keys(ArcModelType);

  projectMetadataCollection: IProjectMetadata[] = [];
  arcTemplatesSharedCollection: IArcTemplate[] = [];

  editForm: ArcMetadataFormGroup = this.arcMetadataFormService.createArcMetadataFormGroup();

  constructor(
    protected arcMetadataService: ArcMetadataService,
    protected arcMetadataFormService: ArcMetadataFormService,
    protected projectMetadataService: ProjectMetadataService,
    protected arcTemplateService: ArcTemplateService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProjectMetadata = (o1: IProjectMetadata | null, o2: IProjectMetadata | null): boolean =>
    this.projectMetadataService.compareProjectMetadata(o1, o2);

  compareArcTemplate = (o1: IArcTemplate | null, o2: IArcTemplate | null): boolean => this.arcTemplateService.compareArcTemplate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arcMetadata }) => {
      this.arcMetadata = arcMetadata;
      if (arcMetadata) {
        this.updateForm(arcMetadata);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arcMetadata = this.arcMetadataFormService.getArcMetadata(this.editForm);
    if (arcMetadata.id !== null) {
      this.subscribeToSaveResponse(this.arcMetadataService.update(arcMetadata));
    } else {
      this.subscribeToSaveResponse(this.arcMetadataService.create(arcMetadata));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArcMetadata>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(arcMetadata: IArcMetadata): void {
    this.arcMetadata = arcMetadata;
    this.arcMetadataFormService.resetForm(this.editForm, arcMetadata);

    this.projectMetadataCollection = this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
      this.projectMetadataCollection,
      arcMetadata.projectMetadata
    );
    this.arcTemplatesSharedCollection = this.arcTemplateService.addArcTemplateToCollectionIfMissing<IArcTemplate>(
      this.arcTemplatesSharedCollection,
      arcMetadata.arcTemplate
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectMetadataService
      .query({ filter: 'arcmetadata-is-null' })
      .pipe(map((res: HttpResponse<IProjectMetadata[]>) => res.body ?? []))
      .pipe(
        map((projectMetadata: IProjectMetadata[]) =>
          this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
            projectMetadata,
            this.arcMetadata?.projectMetadata
          )
        )
      )
      .subscribe((projectMetadata: IProjectMetadata[]) => (this.projectMetadataCollection = projectMetadata));

    this.arcTemplateService
      .query()
      .pipe(map((res: HttpResponse<IArcTemplate[]>) => res.body ?? []))
      .pipe(
        map((arcTemplates: IArcTemplate[]) =>
          this.arcTemplateService.addArcTemplateToCollectionIfMissing<IArcTemplate>(arcTemplates, this.arcMetadata?.arcTemplate)
        )
      )
      .subscribe((arcTemplates: IArcTemplate[]) => (this.arcTemplatesSharedCollection = arcTemplates));
  }
}
