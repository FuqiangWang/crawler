import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CicdMetadataComponent } from '../list/cicd-metadata.component';
import { CicdMetadataDetailComponent } from '../detail/cicd-metadata-detail.component';
import { CicdMetadataUpdateComponent } from '../update/cicd-metadata-update.component';
import { CicdMetadataRoutingResolveService } from './cicd-metadata-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cicdMetadataRoute: Routes = [
  {
    path: '',
    component: CicdMetadataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CicdMetadataDetailComponent,
    resolve: {
      cicdMetadata: CicdMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CicdMetadataUpdateComponent,
    resolve: {
      cicdMetadata: CicdMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CicdMetadataUpdateComponent,
    resolve: {
      cicdMetadata: CicdMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cicdMetadataRoute)],
  exports: [RouterModule],
})
export class CicdMetadataRoutingModule {}
