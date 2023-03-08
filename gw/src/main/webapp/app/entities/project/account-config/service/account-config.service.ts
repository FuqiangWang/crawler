import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccountConfig, NewAccountConfig } from '../account-config.model';

export type PartialUpdateAccountConfig = Partial<IAccountConfig> & Pick<IAccountConfig, 'id'>;

export type EntityResponseType = HttpResponse<IAccountConfig>;
export type EntityArrayResponseType = HttpResponse<IAccountConfig[]>;

@Injectable({ providedIn: 'root' })
export class AccountConfigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/account-configs', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accountConfig: NewAccountConfig): Observable<EntityResponseType> {
    return this.http.post<IAccountConfig>(this.resourceUrl, accountConfig, { observe: 'response' });
  }

  update(accountConfig: IAccountConfig): Observable<EntityResponseType> {
    return this.http.put<IAccountConfig>(`${this.resourceUrl}/${this.getAccountConfigIdentifier(accountConfig)}`, accountConfig, {
      observe: 'response',
    });
  }

  partialUpdate(accountConfig: PartialUpdateAccountConfig): Observable<EntityResponseType> {
    return this.http.patch<IAccountConfig>(`${this.resourceUrl}/${this.getAccountConfigIdentifier(accountConfig)}`, accountConfig, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccountConfigIdentifier(accountConfig: Pick<IAccountConfig, 'id'>): number {
    return accountConfig.id;
  }

  compareAccountConfig(o1: Pick<IAccountConfig, 'id'> | null, o2: Pick<IAccountConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getAccountConfigIdentifier(o1) === this.getAccountConfigIdentifier(o2) : o1 === o2;
  }

  addAccountConfigToCollectionIfMissing<Type extends Pick<IAccountConfig, 'id'>>(
    accountConfigCollection: Type[],
    ...accountConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accountConfigs: Type[] = accountConfigsToCheck.filter(isPresent);
    if (accountConfigs.length > 0) {
      const accountConfigCollectionIdentifiers = accountConfigCollection.map(
        accountConfigItem => this.getAccountConfigIdentifier(accountConfigItem)!
      );
      const accountConfigsToAdd = accountConfigs.filter(accountConfigItem => {
        const accountConfigIdentifier = this.getAccountConfigIdentifier(accountConfigItem);
        if (accountConfigCollectionIdentifiers.includes(accountConfigIdentifier)) {
          return false;
        }
        accountConfigCollectionIdentifiers.push(accountConfigIdentifier);
        return true;
      });
      return [...accountConfigsToAdd, ...accountConfigCollection];
    }
    return accountConfigCollection;
  }
}
