import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AccountConfigFormService, AccountConfigFormGroup } from './account-config-form.service';
import { IAccountConfig } from '../account-config.model';
import { AccountConfigService } from '../service/account-config.service';

@Component({
  selector: 'jhi-account-config-update',
  templateUrl: './account-config-update.component.html',
})
export class AccountConfigUpdateComponent implements OnInit {
  isSaving = false;
  accountConfig: IAccountConfig | null = null;

  editForm: AccountConfigFormGroup = this.accountConfigFormService.createAccountConfigFormGroup();

  constructor(
    protected accountConfigService: AccountConfigService,
    protected accountConfigFormService: AccountConfigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountConfig }) => {
      this.accountConfig = accountConfig;
      if (accountConfig) {
        this.updateForm(accountConfig);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountConfig = this.accountConfigFormService.getAccountConfig(this.editForm);
    if (accountConfig.id !== null) {
      this.subscribeToSaveResponse(this.accountConfigService.update(accountConfig));
    } else {
      this.subscribeToSaveResponse(this.accountConfigService.create(accountConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountConfig>>): void {
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

  protected updateForm(accountConfig: IAccountConfig): void {
    this.accountConfig = accountConfig;
    this.accountConfigFormService.resetForm(this.editForm, accountConfig);
  }
}
