import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccountConfigComponent } from '../list/account-config.component';
import { AccountConfigDetailComponent } from '../detail/account-config-detail.component';
import { AccountConfigUpdateComponent } from '../update/account-config-update.component';
import { AccountConfigRoutingResolveService } from './account-config-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const accountConfigRoute: Routes = [
  {
    path: '',
    component: AccountConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountConfigDetailComponent,
    resolve: {
      accountConfig: AccountConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountConfigUpdateComponent,
    resolve: {
      accountConfig: AccountConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountConfigUpdateComponent,
    resolve: {
      accountConfig: AccountConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accountConfigRoute)],
  exports: [RouterModule],
})
export class AccountConfigRoutingModule {}
