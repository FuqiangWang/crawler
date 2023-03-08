import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICicdMetadata, NewCicdMetadata } from '../cicd-metadata.model';

export type PartialUpdateCicdMetadata = Partial<ICicdMetadata> & Pick<ICicdMetadata, 'id'>;

export type EntityResponseType = HttpResponse<ICicdMetadata>;
export type EntityArrayResponseType = HttpResponse<ICicdMetadata[]>;

@Injectable({ providedIn: 'root' })
export class CicdMetadataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cicd-metadata', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cicdMetadata: NewCicdMetadata): Observable<EntityResponseType> {
    return this.http.post<ICicdMetadata>(this.resourceUrl, cicdMetadata, { observe: 'response' });
  }

  update(cicdMetadata: ICicdMetadata): Observable<EntityResponseType> {
    return this.http.put<ICicdMetadata>(`${this.resourceUrl}/${this.getCicdMetadataIdentifier(cicdMetadata)}`, cicdMetadata, {
      observe: 'response',
    });
  }

  partialUpdate(cicdMetadata: PartialUpdateCicdMetadata): Observable<EntityResponseType> {
    return this.http.patch<ICicdMetadata>(`${this.resourceUrl}/${this.getCicdMetadataIdentifier(cicdMetadata)}`, cicdMetadata, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICicdMetadata>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICicdMetadata[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCicdMetadataIdentifier(cicdMetadata: Pick<ICicdMetadata, 'id'>): number {
    return cicdMetadata.id;
  }

  compareCicdMetadata(o1: Pick<ICicdMetadata, 'id'> | null, o2: Pick<ICicdMetadata, 'id'> | null): boolean {
    return o1 && o2 ? this.getCicdMetadataIdentifier(o1) === this.getCicdMetadataIdentifier(o2) : o1 === o2;
  }

  addCicdMetadataToCollectionIfMissing<Type extends Pick<ICicdMetadata, 'id'>>(
    cicdMetadataCollection: Type[],
    ...cicdMetadataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cicdMetadata: Type[] = cicdMetadataToCheck.filter(isPresent);
    if (cicdMetadata.length > 0) {
      const cicdMetadataCollectionIdentifiers = cicdMetadataCollection.map(
        cicdMetadataItem => this.getCicdMetadataIdentifier(cicdMetadataItem)!
      );
      const cicdMetadataToAdd = cicdMetadata.filter(cicdMetadataItem => {
        const cicdMetadataIdentifier = this.getCicdMetadataIdentifier(cicdMetadataItem);
        if (cicdMetadataCollectionIdentifiers.includes(cicdMetadataIdentifier)) {
          return false;
        }
        cicdMetadataCollectionIdentifiers.push(cicdMetadataIdentifier);
        return true;
      });
      return [...cicdMetadataToAdd, ...cicdMetadataCollection];
    }
    return cicdMetadataCollection;
  }
}
