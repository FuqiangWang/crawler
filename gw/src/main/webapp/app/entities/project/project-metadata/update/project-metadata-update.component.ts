import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProjectMetadataFormService, ProjectMetadataFormGroup } from './project-metadata-form.service';
import { IProjectMetadata } from '../project-metadata.model';
import { ProjectMetadataService } from '../service/project-metadata.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ProjectType } from 'app/entities/enumerations/project-type.model';

@Component({
  selector: 'jhi-project-metadata-update',
  templateUrl: './project-metadata-update.component.html',
})
export class ProjectMetadataUpdateComponent implements OnInit {
  isSaving = false;
  projectMetadata: IProjectMetadata | null = null;
  projectTypeValues = Object.keys(ProjectType);

  editForm: ProjectMetadataFormGroup = this.projectMetadataFormService.createProjectMetadataFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projectMetadataService: ProjectMetadataService,
    protected projectMetadataFormService: ProjectMetadataFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectMetadata }) => {
      this.projectMetadata = projectMetadata;
      if (projectMetadata) {
        this.updateForm(projectMetadata);
      }
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
    const projectMetadata = this.projectMetadataFormService.getProjectMetadata(this.editForm);
    if (projectMetadata.id !== null) {
      this.subscribeToSaveResponse(this.projectMetadataService.update(projectMetadata));
    } else {
      this.subscribeToSaveResponse(this.projectMetadataService.create(projectMetadata));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectMetadata>>): void {
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

  protected updateForm(projectMetadata: IProjectMetadata): void {
    this.projectMetadata = projectMetadata;
    this.projectMetadataFormService.resetForm(this.editForm, projectMetadata);
  }
}
