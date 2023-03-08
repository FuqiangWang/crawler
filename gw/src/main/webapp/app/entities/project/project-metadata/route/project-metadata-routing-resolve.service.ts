import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectMetadata } from '../project-metadata.model';
import { ProjectMetadataService } from '../service/project-metadata.service';

@Injectable({ providedIn: 'root' })
export class ProjectMetadataRoutingResolveService implements Resolve<IProjectMetadata | null> {
  constructor(protected service: ProjectMetadataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectMetadata | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectMetadata: HttpResponse<IProjectMetadata>) => {
          if (projectMetadata.body) {
            return of(projectMetadata.body);
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
