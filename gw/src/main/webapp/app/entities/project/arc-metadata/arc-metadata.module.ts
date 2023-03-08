import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArcMetadataComponent } from './list/arc-metadata.component';
import { ArcMetadataDetailComponent } from './detail/arc-metadata-detail.component';
import { ArcMetadataUpdateComponent } from './update/arc-metadata-update.component';
import { ArcMetadataDeleteDialogComponent } from './delete/arc-metadata-delete-dialog.component';
import { ArcMetadataRoutingModule } from './route/arc-metadata-routing.module';

@NgModule({
  imports: [SharedModule, ArcMetadataRoutingModule],
  declarations: [ArcMetadataComponent, ArcMetadataDetailComponent, ArcMetadataUpdateComponent, ArcMetadataDeleteDialogComponent],
})
export class ProjectArcMetadataModule {}
