import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArcTemplateComponent } from './list/arc-template.component';
import { ArcTemplateDetailComponent } from './detail/arc-template-detail.component';
import { ArcTemplateUpdateComponent } from './update/arc-template-update.component';
import { ArcTemplateDeleteDialogComponent } from './delete/arc-template-delete-dialog.component';
import { ArcTemplateRoutingModule } from './route/arc-template-routing.module';

@NgModule({
  imports: [SharedModule, ArcTemplateRoutingModule],
  declarations: [ArcTemplateComponent, ArcTemplateDetailComponent, ArcTemplateUpdateComponent, ArcTemplateDeleteDialogComponent],
})
export class ProjectArcTemplateModule {}
