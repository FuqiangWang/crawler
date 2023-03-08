import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ArcTemplateFormService, ArcTemplateFormGroup } from './arc-template-form.service';
import { IArcTemplate } from '../arc-template.model';
import { ArcTemplateService } from '../service/arc-template.service';

@Component({
  selector: 'jhi-arc-template-update',
  templateUrl: './arc-template-update.component.html',
})
export class ArcTemplateUpdateComponent implements OnInit {
  isSaving = false;
  arcTemplate: IArcTemplate | null = null;

  editForm: ArcTemplateFormGroup = this.arcTemplateFormService.createArcTemplateFormGroup();

  constructor(
    protected arcTemplateService: ArcTemplateService,
    protected arcTemplateFormService: ArcTemplateFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arcTemplate }) => {
      this.arcTemplate = arcTemplate;
      if (arcTemplate) {
        this.updateForm(arcTemplate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arcTemplate = this.arcTemplateFormService.getArcTemplate(this.editForm);
    if (arcTemplate.id !== null) {
      this.subscribeToSaveResponse(this.arcTemplateService.update(arcTemplate));
    } else {
      this.subscribeToSaveResponse(this.arcTemplateService.create(arcTemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArcTemplate>>): void {
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

  protected updateForm(arcTemplate: IArcTemplate): void {
    this.arcTemplate = arcTemplate;
    this.arcTemplateFormService.resetForm(this.editForm, arcTemplate);
  }
}
