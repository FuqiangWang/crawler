import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CicdMetadataComponent } from './list/cicd-metadata.component';
import { CicdMetadataDetailComponent } from './detail/cicd-metadata-detail.component';
import { CicdMetadataUpdateComponent } from './update/cicd-metadata-update.component';
import { CicdMetadataDeleteDialogComponent } from './delete/cicd-metadata-delete-dialog.component';
import { CicdMetadataRoutingModule } from './route/cicd-metadata-routing.module';

@NgModule({
  imports: [SharedModule, CicdMetadataRoutingModule],
  declarations: [CicdMetadataComponent, CicdMetadataDetailComponent, CicdMetadataUpdateComponent, CicdMetadataDeleteDialogComponent],
})
export class ProjectCicdMetadataModule {}
