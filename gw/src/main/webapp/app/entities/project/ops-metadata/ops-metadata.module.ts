import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OpsMetadataComponent } from './list/ops-metadata.component';
import { OpsMetadataDetailComponent } from './detail/ops-metadata-detail.component';
import { OpsMetadataUpdateComponent } from './update/ops-metadata-update.component';
import { OpsMetadataDeleteDialogComponent } from './delete/ops-metadata-delete-dialog.component';
import { OpsMetadataRoutingModule } from './route/ops-metadata-routing.module';

@NgModule({
  imports: [SharedModule, OpsMetadataRoutingModule],
  declarations: [OpsMetadataComponent, OpsMetadataDetailComponent, OpsMetadataUpdateComponent, OpsMetadataDeleteDialogComponent],
})
export class ProjectOpsMetadataModule {}
