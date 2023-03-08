import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOpsMetadata } from '../ops-metadata.model';
import { OpsMetadataService } from '../service/ops-metadata.service';

@Injectable({ providedIn: 'root' })
export class OpsMetadataRoutingResolveService implements Resolve<IOpsMetadata | null> {
  constructor(protected service: OpsMetadataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOpsMetadata | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((opsMetadata: HttpResponse<IOpsMetadata>) => {
          if (opsMetadata.body) {
            return of(opsMetadata.body);
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
