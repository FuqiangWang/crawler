import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectMetadata, NewProjectMetadata } from '../project-metadata.model';

export type PartialUpdateProjectMetadata = Partial<IProjectMetadata> & Pick<IProjectMetadata, 'id'>;

export type EntityResponseType = HttpResponse<IProjectMetadata>;
export type EntityArrayResponseType = HttpResponse<IProjectMetadata[]>;

@Injectable({ providedIn: 'root' })
export class ProjectMetadataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-metadata', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectMetadata: NewProjectMetadata): Observable<EntityResponseType> {
    return this.http.post<IProjectMetadata>(this.resourceUrl, projectMetadata, { observe: 'response' });
  }

  update(projectMetadata: IProjectMetadata): Observable<EntityResponseType> {
    return this.http.put<IProjectMetadata>(`${this.resourceUrl}/${this.getProjectMetadataIdentifier(projectMetadata)}`, projectMetadata, {
      observe: 'response',
    });
  }

  partialUpdate(projectMetadata: PartialUpdateProjectMetadata): Observable<EntityResponseType> {
    return this.http.patch<IProjectMetadata>(`${this.resourceUrl}/${this.getProjectMetadataIdentifier(projectMetadata)}`, projectMetadata, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectMetadata>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectMetadata[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectMetadataIdentifier(projectMetadata: Pick<IProjectMetadata, 'id'>): number {
    return projectMetadata.id;
  }

  compareProjectMetadata(o1: Pick<IProjectMetadata, 'id'> | null, o2: Pick<IProjectMetadata, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectMetadataIdentifier(o1) === this.getProjectMetadataIdentifier(o2) : o1 === o2;
  }

  addProjectMetadataToCollectionIfMissing<Type extends Pick<IProjectMetadata, 'id'>>(
    projectMetadataCollection: Type[],
    ...projectMetadataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projectMetadata: Type[] = projectMetadataToCheck.filter(isPresent);
    if (projectMetadata.length > 0) {
      const projectMetadataCollectionIdentifiers = projectMetadataCollection.map(
        projectMetadataItem => this.getProjectMetadataIdentifier(projectMetadataItem)!
      );
      const projectMetadataToAdd = projectMetadata.filter(projectMetadataItem => {
        const projectMetadataIdentifier = this.getProjectMetadataIdentifier(projectMetadataItem);
        if (projectMetadataCollectionIdentifiers.includes(projectMetadataIdentifier)) {
          return false;
        }
        projectMetadataCollectionIdentifiers.push(projectMetadataIdentifier);
        return true;
      });
      return [...projectMetadataToAdd, ...projectMetadataCollection];
    }
    return projectMetadataCollection;
  }
}
