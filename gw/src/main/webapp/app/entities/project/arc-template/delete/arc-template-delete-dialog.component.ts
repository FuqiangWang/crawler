import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IArcTemplate } from '../arc-template.model';
import { ArcTemplateService } from '../service/arc-template.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './arc-template-delete-dialog.component.html',
})
export class ArcTemplateDeleteDialogComponent {
  arcTemplate?: IArcTemplate;

  constructor(protected arcTemplateService: ArcTemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arcTemplateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
