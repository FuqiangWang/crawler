import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccountConfigComponent } from './list/account-config.component';
import { AccountConfigDetailComponent } from './detail/account-config-detail.component';
import { AccountConfigUpdateComponent } from './update/account-config-update.component';
import { AccountConfigDeleteDialogComponent } from './delete/account-config-delete-dialog.component';
import { AccountConfigRoutingModule } from './route/account-config-routing.module';

@NgModule({
  imports: [SharedModule, AccountConfigRoutingModule],
  declarations: [AccountConfigComponent, AccountConfigDetailComponent, AccountConfigUpdateComponent, AccountConfigDeleteDialogComponent],
})
export class ProjectAccountConfigModule {}
