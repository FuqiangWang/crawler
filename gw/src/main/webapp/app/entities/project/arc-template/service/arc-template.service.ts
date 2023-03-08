import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArcTemplate, NewArcTemplate } from '../arc-template.model';

export type PartialUpdateArcTemplate = Partial<IArcTemplate> & Pick<IArcTemplate, 'id'>;

export type EntityResponseType = HttpResponse<IArcTemplate>;
export type EntityArrayResponseType = HttpResponse<IArcTemplate[]>;

@Injectable({ providedIn: 'root' })
export class ArcTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/arc-templates', 'project');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(arcTemplate: NewArcTemplate): Observable<EntityResponseType> {
    return this.http.post<IArcTemplate>(this.resourceUrl, arcTemplate, { observe: 'response' });
  }

  update(arcTemplate: IArcTemplate): Observable<EntityResponseType> {
    return this.http.put<IArcTemplate>(`${this.resourceUrl}/${this.getArcTemplateIdentifier(arcTemplate)}`, arcTemplate, {
      observe: 'response',
    });
  }

  partialUpdate(arcTemplate: PartialUpdateArcTemplate): Observable<EntityResponseType> {
    return this.http.patch<IArcTemplate>(`${this.resourceUrl}/${this.getArcTemplateIdentifier(arcTemplate)}`, arcTemplate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArcTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArcTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArcTemplateIdentifier(arcTemplate: Pick<IArcTemplate, 'id'>): number {
    return arcTemplate.id;
  }

  compareArcTemplate(o1: Pick<IArcTemplate, 'id'> | null, o2: Pick<IArcTemplate, 'id'> | null): boolean {
    return o1 && o2 ? this.getArcTemplateIdentifier(o1) === this.getArcTemplateIdentifier(o2) : o1 === o2;
  }

  addArcTemplateToCollectionIfMissing<Type extends Pick<IArcTemplate, 'id'>>(
    arcTemplateCollection: Type[],
    ...arcTemplatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const arcTemplates: Type[] = arcTemplatesToCheck.filter(isPresent);
    if (arcTemplates.length > 0) {
      const arcTemplateCollectionIdentifiers = arcTemplateCollection.map(
        arcTemplateItem => this.getArcTemplateIdentifier(arcTemplateItem)!
      );
      const arcTemplatesToAdd = arcTemplates.filter(arcTemplateItem => {
        const arcTemplateIdentifier = this.getArcTemplateIdentifier(arcTemplateItem);
        if (arcTemplateCollectionIdentifiers.includes(arcTemplateIdentifier)) {
          return false;
        }
        arcTemplateCollectionIdentifiers.push(arcTemplateIdentifier);
        return true;
      });
      return [...arcTemplatesToAdd, ...arcTemplateCollection];
    }
    return arcTemplateCollection;
  }
}
