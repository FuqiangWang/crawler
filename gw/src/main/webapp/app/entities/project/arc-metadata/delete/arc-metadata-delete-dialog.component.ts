import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IArcMetadata } from '../arc-metadata.model';
import { ArcMetadataService } from '../service/arc-metadata.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './arc-metadata-delete-dialog.component.html',
})
export class ArcMetadataDeleteDialogComponent {
  arcMetadata?: IArcMetadata;

  constructor(protected arcMetadataService: ArcMetadataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arcMetadataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
