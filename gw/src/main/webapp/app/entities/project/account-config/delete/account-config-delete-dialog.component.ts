import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountConfig } from '../account-config.model';
import { AccountConfigService } from '../service/account-config.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './account-config-delete-dialog.component.html',
})
export class AccountConfigDeleteDialogComponent {
  accountConfig?: IAccountConfig;

  constructor(protected accountConfigService: AccountConfigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
