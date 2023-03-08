import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectMetadata } from '../project-metadata.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-project-metadata-detail',
  templateUrl: './project-metadata-detail.component.html',
})
export class ProjectMetadataDetailComponent implements OnInit {
  projectMetadata: IProjectMetadata | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectMetadata }) => {
      this.projectMetadata = projectMetadata;
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
