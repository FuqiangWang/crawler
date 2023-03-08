import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArcMetadata, NewArcMetadata } from '../arc-metadata.model';

export type PartialUpdateArcMetadata = Partial<IArcMetadata> & Pick<IArcMetadata, 'id'>;

export type EntityResponseType = HttpResponse<IArcMetadata>;
export type EntityArrayResponseType = HttpResponse<IArcMetadata[]>;

@Injectable({ providedIn: 'root' })
export class ArcMetadataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/arc-metadata', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(arcMetadata: NewArcMetadata): Observable<EntityResponseType> {
    return this.http.post<IArcMetadata>(this.resourceUrl, arcMetadata, { observe: 'response' });
  }

  update(arcMetadata: IArcMetadata): Observable<EntityResponseType> {
    return this.http.put<IArcMetadata>(`${this.resourceUrl}/${this.getArcMetadataIdentifier(arcMetadata)}`, arcMetadata, {
      observe: 'response',
    });
  }

  partialUpdate(arcMetadata: PartialUpdateArcMetadata): Observable<EntityResponseType> {
    return this.http.patch<IArcMetadata>(`${this.resourceUrl}/${this.getArcMetadataIdentifier(arcMetadata)}`, arcMetadata, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArcMetadata>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArcMetadata[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArcMetadataIdentifier(arcMetadata: Pick<IArcMetadata, 'id'>): number {
    return arcMetadata.id;
  }

  compareArcMetadata(o1: Pick<IArcMetadata, 'id'> | null, o2: Pick<IArcMetadata, 'id'> | null): boolean {
    return o1 && o2 ? this.getArcMetadataIdentifier(o1) === this.getArcMetadataIdentifier(o2) : o1 === o2;
  }

  addArcMetadataToCollectionIfMissing<Type extends Pick<IArcMetadata, 'id'>>(
    arcMetadataCollection: Type[],
    ...arcMetadataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const arcMetadata: Type[] = arcMetadataToCheck.filter(isPresent);
    if (arcMetadata.length > 0) {
      const arcMetadataCollectionIdentifiers = arcMetadataCollection.map(
        arcMetadataItem => this.getArcMetadataIdentifier(arcMetadataItem)!
      );
      const arcMetadataToAdd = arcMetadata.filter(arcMetadataItem => {
        const arcMetadataIdentifier = this.getArcMetadataIdentifier(arcMetadataItem);
        if (arcMetadataCollectionIdentifiers.includes(arcMetadataIdentifier)) {
          return false;
        }
        arcMetadataCollectionIdentifiers.push(arcMetadataIdentifier);
        return true;
      });
      return [...arcMetadataToAdd, ...arcMetadataCollection];
    }
    return arcMetadataCollection;
  }
}
