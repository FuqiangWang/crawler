import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CicdMetadataFormService, CicdMetadataFormGroup } from './cicd-metadata-form.service';
import { ICicdMetadata } from '../cicd-metadata.model';
import { CicdMetadataService } from '../service/cicd-metadata.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { ProjectMetadataService } from 'app/entities/project/project-metadata/service/project-metadata.service';
import { CdType } from 'app/entities/enumerations/cd-type.model';

@Component({
  selector: 'jhi-cicd-metadata-update',
  templateUrl: './cicd-metadata-update.component.html',
})
export class CicdMetadataUpdateComponent implements OnInit {
  isSaving = false;
  cicdMetadata: ICicdMetadata | null = null;
  cdTypeValues = Object.keys(CdType);

  projectMetadataCollection: IProjectMetadata[] = [];

  editForm: CicdMetadataFormGroup = this.cicdMetadataFormService.createCicdMetadataFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected cicdMetadataService: CicdMetadataService,
    protected cicdMetadataFormService: CicdMetadataFormService,
    protected projectMetadataService: ProjectMetadataService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProjectMetadata = (o1: IProjectMetadata | null, o2: IProjectMetadata | null): boolean =>
    this.projectMetadataService.compareProjectMetadata(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cicdMetadata }) => {
      this.cicdMetadata = cicdMetadata;
      if (cicdMetadata) {
        this.updateForm(cicdMetadata);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('gwApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cicdMetadata = this.cicdMetadataFormService.getCicdMetadata(this.editForm);
    if (cicdMetadata.id !== null) {
      this.subscribeToSaveResponse(this.cicdMetadataService.update(cicdMetadata));
    } else {
      this.subscribeToSaveResponse(this.cicdMetadataService.create(cicdMetadata));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICicdMetadata>>): void {
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

  protected updateForm(cicdMetadata: ICicdMetadata): void {
    this.cicdMetadata = cicdMetadata;
    this.cicdMetadataFormService.resetForm(this.editForm, cicdMetadata);

    this.projectMetadataCollection = this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
      this.projectMetadataCollection,
      cicdMetadata.projectMetadata
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectMetadataService
      .query({ filter: 'cicdmetadata-is-null' })
      .pipe(map((res: HttpResponse<IProjectMetadata[]>) => res.body ?? []))
      .pipe(
        map((projectMetadata: IProjectMetadata[]) =>
          this.projectMetadataService.addProjectMetadataToCollectionIfMissing<IProjectMetadata>(
            projectMetadata,
            this.cicdMetadata?.projectMetadata
          )
        )
      )
      .subscribe((projectMetadata: IProjectMetadata[]) => (this.projectMetadataCollection = projectMetadata));
  }
}
