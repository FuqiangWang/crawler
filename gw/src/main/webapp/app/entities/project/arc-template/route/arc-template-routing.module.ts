import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArcTemplateComponent } from '../list/arc-template.component';
import { ArcTemplateDetailComponent } from '../detail/arc-template-detail.component';
import { ArcTemplateUpdateComponent } from '../update/arc-template-update.component';
import { ArcTemplateRoutingResolveService } from './arc-template-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const arcTemplateRoute: Routes = [
  {
    path: '',
    component: ArcTemplateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArcTemplateDetailComponent,
    resolve: {
      arcTemplate: ArcTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArcTemplateUpdateComponent,
    resolve: {
      arcTemplate: ArcTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArcTemplateUpdateComponent,
    resolve: {
      arcTemplate: ArcTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(arcTemplateRoute)],
  exports: [RouterModule],
})
export class ArcTemplateRoutingModule {}
