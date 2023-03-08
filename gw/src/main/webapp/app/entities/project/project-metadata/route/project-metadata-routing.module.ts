import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectMetadataComponent } from '../list/project-metadata.component';
import { ProjectMetadataDetailComponent } from '../detail/project-metadata-detail.component';
import { ProjectMetadataUpdateComponent } from '../update/project-metadata-update.component';
import { ProjectMetadataRoutingResolveService } from './project-metadata-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectMetadataRoute: Routes = [
  {
    path: '',
    component: ProjectMetadataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectMetadataDetailComponent,
    resolve: {
      projectMetadata: ProjectMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectMetadataUpdateComponent,
    resolve: {
      projectMetadata: ProjectMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectMetadataUpdateComponent,
    resolve: {
      projectMetadata: ProjectMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectMetadataRoute)],
  exports: [RouterModule],
})
export class ProjectMetadataRoutingModule {}
