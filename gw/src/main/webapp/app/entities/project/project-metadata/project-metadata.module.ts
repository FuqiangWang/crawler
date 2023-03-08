import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectMetadataComponent } from './list/project-metadata.component';
import { ProjectMetadataDetailComponent } from './detail/project-metadata-detail.component';
import { ProjectMetadataUpdateComponent } from './update/project-metadata-update.component';
import { ProjectMetadataDeleteDialogComponent } from './delete/project-metadata-delete-dialog.component';
import { ProjectMetadataRoutingModule } from './route/project-metadata-routing.module';

@NgModule({
  imports: [SharedModule, ProjectMetadataRoutingModule],
  declarations: [
    ProjectMetadataComponent,
    ProjectMetadataDetailComponent,
    ProjectMetadataUpdateComponent,
    ProjectMetadataDeleteDialogComponent,
  ],
})
export class ProjectProjectMetadataModule {}
