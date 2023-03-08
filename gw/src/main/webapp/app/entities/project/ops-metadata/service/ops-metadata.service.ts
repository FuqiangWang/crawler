import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOpsMetadata, NewOpsMetadata } from '../ops-metadata.model';

export type PartialUpdateOpsMetadata = Partial<IOpsMetadata> & Pick<IOpsMetadata, 'id'>;

export type EntityResponseType = HttpResponse<IOpsMetadata>;
export type EntityArrayResponseType = HttpResponse<IOpsMetadata[]>;

@Injectable({ providedIn: 'root' })
export class OpsMetadataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ops-metadata', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(opsMetadata: NewOpsMetadata): Observable<EntityResponseType> {
    return this.http.post<IOpsMetadata>(this.resourceUrl, opsMetadata, { observe: 'response' });
  }

  update(opsMetadata: IOpsMetadata): Observable<EntityResponseType> {
    return this.http.put<IOpsMetadata>(`${this.resourceUrl}/${this.getOpsMetadataIdentifier(opsMetadata)}`, opsMetadata, {
      observe: 'response',
    });
  }

  partialUpdate(opsMetadata: PartialUpdateOpsMetadata): Observable<EntityResponseType> {
    return this.http.patch<IOpsMetadata>(`${this.resourceUrl}/${this.getOpsMetadataIdentifier(opsMetadata)}`, opsMetadata, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpsMetadata>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpsMetadata[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOpsMetadataIdentifier(opsMetadata: Pick<IOpsMetadata, 'id'>): number {
    return opsMetadata.id;
  }

  compareOpsMetadata(o1: Pick<IOpsMetadata, 'id'> | null, o2: Pick<IOpsMetadata, 'id'> | null): boolean {
    return o1 && o2 ? this.getOpsMetadataIdentifier(o1) === this.getOpsMetadataIdentifier(o2) : o1 === o2;
  }

  addOpsMetadataToCollectionIfMissing<Type extends Pick<IOpsMetadata, 'id'>>(
    opsMetadataCollection: Type[],
    ...opsMetadataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const opsMetadata: Type[] = opsMetadataToCheck.filter(isPresent);
    if (opsMetadata.length > 0) {
      const opsMetadataCollectionIdentifiers = opsMetadataCollection.map(
        opsMetadataItem => this.getOpsMetadataIdentifier(opsMetadataItem)!
      );
      const opsMetadataToAdd = opsMetadata.filter(opsMetadataItem => {
        const opsMetadataIdentifier = this.getOpsMetadataIdentifier(opsMetadataItem);
        if (opsMetadataCollectionIdentifiers.includes(opsMetadataIdentifier)) {
          return false;
        }
        opsMetadataCollectionIdentifiers.push(opsMetadataIdentifier);
        return true;
      });
      return [...opsMetadataToAdd, ...opsMetadataCollection];
    }
    return opsMetadataCollection;
  }
}
