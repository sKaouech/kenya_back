import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonInfo, NewPersonInfo } from '../person-info.model';

export type PartialUpdatePersonInfo = Partial<IPersonInfo> & Pick<IPersonInfo, 'id'>;

type RestOf<T extends IPersonInfo | NewPersonInfo> = Omit<T, 'nationalIdIssueDate' | 'passportIssueDate' | 'driveLicenceIssueDate'> & {
  nationalIdIssueDate?: string | null;
  passportIssueDate?: string | null;
  driveLicenceIssueDate?: string | null;
};

export type RestPersonInfo = RestOf<IPersonInfo>;

export type NewRestPersonInfo = RestOf<NewPersonInfo>;

export type PartialUpdateRestPersonInfo = RestOf<PartialUpdatePersonInfo>;

export type EntityResponseType = HttpResponse<IPersonInfo>;
export type EntityArrayResponseType = HttpResponse<IPersonInfo[]>;

@Injectable({ providedIn: 'root' })
export class PersonInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personInfo: NewPersonInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInfo);
    return this.http
      .post<RestPersonInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(personInfo: IPersonInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInfo);
    return this.http
      .put<RestPersonInfo>(`${this.resourceUrl}/${this.getPersonInfoIdentifier(personInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(personInfo: PartialUpdatePersonInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInfo);
    return this.http
      .patch<RestPersonInfo>(`${this.resourceUrl}/${this.getPersonInfoIdentifier(personInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPersonInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPersonInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonInfoIdentifier(personInfo: Pick<IPersonInfo, 'id'>): number {
    return personInfo.id;
  }

  comparePersonInfo(o1: Pick<IPersonInfo, 'id'> | null, o2: Pick<IPersonInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonInfoIdentifier(o1) === this.getPersonInfoIdentifier(o2) : o1 === o2;
  }

  addPersonInfoToCollectionIfMissing<Type extends Pick<IPersonInfo, 'id'>>(
    personInfoCollection: Type[],
    ...personInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personInfos: Type[] = personInfosToCheck.filter(isPresent);
    if (personInfos.length > 0) {
      const personInfoCollectionIdentifiers = personInfoCollection.map(personInfoItem => this.getPersonInfoIdentifier(personInfoItem)!);
      const personInfosToAdd = personInfos.filter(personInfoItem => {
        const personInfoIdentifier = this.getPersonInfoIdentifier(personInfoItem);
        if (personInfoCollectionIdentifiers.includes(personInfoIdentifier)) {
          return false;
        }
        personInfoCollectionIdentifiers.push(personInfoIdentifier);
        return true;
      });
      return [...personInfosToAdd, ...personInfoCollection];
    }
    return personInfoCollection;
  }

  protected convertDateFromClient<T extends IPersonInfo | NewPersonInfo | PartialUpdatePersonInfo>(personInfo: T): RestOf<T> {
    return {
      ...personInfo,
      nationalIdIssueDate: personInfo.nationalIdIssueDate?.format(DATE_FORMAT) ?? null,
      passportIssueDate: personInfo.passportIssueDate?.format(DATE_FORMAT) ?? null,
      driveLicenceIssueDate: personInfo.driveLicenceIssueDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPersonInfo: RestPersonInfo): IPersonInfo {
    return {
      ...restPersonInfo,
      nationalIdIssueDate: restPersonInfo.nationalIdIssueDate ? dayjs(restPersonInfo.nationalIdIssueDate) : undefined,
      passportIssueDate: restPersonInfo.passportIssueDate ? dayjs(restPersonInfo.passportIssueDate) : undefined,
      driveLicenceIssueDate: restPersonInfo.driveLicenceIssueDate ? dayjs(restPersonInfo.driveLicenceIssueDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPersonInfo>): HttpResponse<IPersonInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPersonInfo[]>): HttpResponse<IPersonInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
