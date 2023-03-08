import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpsMetadata } from '../ops-metadata.model';

@Component({
  selector: 'jhi-ops-metadata-detail',
  templateUrl: './ops-metadata-detail.component.html',
})
export class OpsMetadataDetailComponent implements OnInit {
  opsMetadata: IOpsMetadata | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opsMetadata }) => {
      this.opsMetadata = opsMetadata;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
