import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccountConfig } from '../account-config.model';
import { AccountConfigService } from '../service/account-config.service';

@Injectable({ providedIn: 'root' })
export class AccountConfigRoutingResolveService implements Resolve<IAccountConfig | null> {
  constructor(protected service: AccountConfigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountConfig | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accountConfig: HttpResponse<IAccountConfig>) => {
          if (accountConfig.body) {
            return of(accountConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
