import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArcMetadataComponent } from '../list/arc-metadata.component';
import { ArcMetadataDetailComponent } from '../detail/arc-metadata-detail.component';
import { ArcMetadataUpdateComponent } from '../update/arc-metadata-update.component';
import { ArcMetadataRoutingResolveService } from './arc-metadata-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const arcMetadataRoute: Routes = [
  {
    path: '',
    component: ArcMetadataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArcMetadataDetailComponent,
    resolve: {
      arcMetadata: ArcMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArcMetadataUpdateComponent,
    resolve: {
      arcMetadata: ArcMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArcMetadataUpdateComponent,
    resolve: {
      arcMetadata: ArcMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(arcMetadataRoute)],
  exports: [RouterModule],
})
export class ArcMetadataRoutingModule {}
