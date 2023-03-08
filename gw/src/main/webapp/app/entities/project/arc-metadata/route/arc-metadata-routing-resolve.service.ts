import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArcMetadata } from '../arc-metadata.model';
import { ArcMetadataService } from '../service/arc-metadata.service';

@Injectable({ providedIn: 'root' })
export class ArcMetadataRoutingResolveService implements Resolve<IArcMetadata | null> {
  constructor(protected service: ArcMetadataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArcMetadata | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((arcMetadata: HttpResponse<IArcMetadata>) => {
          if (arcMetadata.body) {
            return of(arcMetadata.body);
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
