import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICicdMetadata } from '../cicd-metadata.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-cicd-metadata-detail',
  templateUrl: './cicd-metadata-detail.component.html',
})
export class CicdMetadataDetailComponent implements OnInit {
  cicdMetadata: ICicdMetadata | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cicdMetadata }) => {
      this.cicdMetadata = cicdMetadata;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
