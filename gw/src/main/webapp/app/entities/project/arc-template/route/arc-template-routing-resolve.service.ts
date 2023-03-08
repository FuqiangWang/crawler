import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArcTemplate } from '../arc-template.model';
import { ArcTemplateService } from '../service/arc-template.service';

@Injectable({ providedIn: 'root' })
export class ArcTemplateRoutingResolveService implements Resolve<IArcTemplate | null> {
  constructor(protected service: ArcTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArcTemplate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((arcTemplate: HttpResponse<IArcTemplate>) => {
          if (arcTemplate.body) {
            return of(arcTemplate.body);
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
