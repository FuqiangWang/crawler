import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICicdMetadata } from '../cicd-metadata.model';
import { CicdMetadataService } from '../service/cicd-metadata.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cicd-metadata-delete-dialog.component.html',
})
export class CicdMetadataDeleteDialogComponent {
  cicdMetadata?: ICicdMetadata;

  constructor(protected cicdMetadataService: CicdMetadataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cicdMetadataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
