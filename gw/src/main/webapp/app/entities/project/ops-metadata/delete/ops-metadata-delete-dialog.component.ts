import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOpsMetadata } from '../ops-metadata.model';
import { OpsMetadataService } from '../service/ops-metadata.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ops-metadata-delete-dialog.component.html',
})
export class OpsMetadataDeleteDialogComponent {
  opsMetadata?: IOpsMetadata;

  constructor(protected opsMetadataService: OpsMetadataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.opsMetadataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
