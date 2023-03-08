import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountConfig } from '../account-config.model';

@Component({
  selector: 'jhi-account-config-detail',
  templateUrl: './account-config-detail.component.html',
})
export class AccountConfigDetailComponent implements OnInit {
  accountConfig: IAccountConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountConfig }) => {
      this.accountConfig = accountConfig;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
