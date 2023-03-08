import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OpsMetadataComponent } from '../list/ops-metadata.component';
import { OpsMetadataDetailComponent } from '../detail/ops-metadata-detail.component';
import { OpsMetadataUpdateComponent } from '../update/ops-metadata-update.component';
import { OpsMetadataRoutingResolveService } from './ops-metadata-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const opsMetadataRoute: Routes = [
  {
    path: '',
    component: OpsMetadataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OpsMetadataDetailComponent,
    resolve: {
      opsMetadata: OpsMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OpsMetadataUpdateComponent,
    resolve: {
      opsMetadata: OpsMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OpsMetadataUpdateComponent,
    resolve: {
      opsMetadata: OpsMetadataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(opsMetadataRoute)],
  exports: [RouterModule],
})
export class OpsMetadataRoutingModule {}
