import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArcMetadata } from '../arc-metadata.model';

@Component({
  selector: 'jhi-arc-metadata-detail',
  templateUrl: './arc-metadata-detail.component.html',
})
export class ArcMetadataDetailComponent implements OnInit {
  arcMetadata: IArcMetadata | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arcMetadata }) => {
      this.arcMetadata = arcMetadata;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
