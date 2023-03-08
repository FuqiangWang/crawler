import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OpsMetadataFormService, OpsMetadataFormGroup } from './ops-metadata-form.service';
import { IOpsMetadata } from '../ops-metadata.model';
import { OpsMetadataService } from '../service/ops-metadata.service';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';

@Component({
  selector: 'jhi-ops-metadata-update',
  templateUrl: './ops-metadata-update.component.html',
})
export class OpsMetadataUpdateComponent implements OnInit {
  isSaving = false;
  opsMetadata: IOpsMetadata | null = null;

  projectMetadataCollection: IProjectMetadata[] = [];

  editForm: OpsMetadataFormGroup = this.opsMetadataFormService.createOpsMetadataFormGroup();

  constructor(
    protected opsMetadataService: OpsMetadataService,
    protected opsMetadataFormService: OpsMetadataFormService,
    protected projectMetadataService: ProjectMetadataService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProjectMetadata = (o1: IProjectMetadata | null, o2: IProjectMetadata | null): boolean =>
    this.projectMetadataService.compareProjectMetadata(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opsMetadata }) => {
      this.opsMetadata = opsMetadata;
      if (opsMetadata) {
        this.updateForm(opsMetadata);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opsMetadata = this.opsMetadataFormService.getOpsMetadata(this.editForm);
    if (opsMetadata.id !== null) {
      this.subscribeToSaveResponse(this.opsMetadataService.update(opsMetadata));
    } else {
      this.subscribeToSaveResponse(this.opsMetadataService.create(opsMetadata));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpsMetadata>>): void {
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

  protected updateForm(opsMetadata: IOpsMetadata): void {
    this.opsMetadata = opsMetadata;
    this.opsMetadataFormService.resetForm(this.editForm, opsMetadata);

    this.projectMetadataCollection = this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
      this.projectMetadataCollection,
      opsMetadata.projectMetadata
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectMetadataService
      .query({ filter: 'opsmetadata-is-null' })
      .pipe(map((res: HttpResponse<IProjectMetadata[]>) => res.body ?? []))
      .pipe(
        map((projectMetadata: IProjectMetadata[]) =>
          this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
            projectMetadata,
            this.opsMetadata?.projectMetadata
          )
        )
      )
      .subscribe((projectMetadata: IProjectMetadata[]) => (this.projectMetadataCollection = projectMetadata));
  }
}
