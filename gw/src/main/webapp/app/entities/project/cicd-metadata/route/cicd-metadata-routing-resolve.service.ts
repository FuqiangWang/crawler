import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICicdMetadata } from '../cicd-metadata.model';
import { CicdMetadataService } from '../service/cicd-metadata.service';

@Injectable({ providedIn: 'root' })
export class CicdMetadataRoutingResolveService implements Resolve<ICicdMetadata | null> {
  constructor(protected service: CicdMetadataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICicdMetadata | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cicdMetadata: HttpResponse<ICicdMetadata>) => {
          if (cicdMetadata.body) {
            return of(cicdMetadata.body);
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
