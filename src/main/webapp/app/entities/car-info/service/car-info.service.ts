import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarInfo, NewCarInfo } from '../car-info.model';

export type PartialUpdateCarInfo = Partial<ICarInfo> & Pick<ICarInfo, 'id'>;

type RestOf<T extends ICarInfo | NewCarInfo> = Omit<
  T,
  'purchaseDate' | 'vehicleTaxValidityDate' | 'originalInServiceDate' | 'insuranceValidityDate'
> & {
  purchaseDate?: string | null;
  vehicleTaxValidityDate?: string | null;
  originalInServiceDate?: string | null;
  insuranceValidityDate?: string | null;
};

export type RestCarInfo = RestOf<ICarInfo>;

export type NewRestCarInfo = RestOf<NewCarInfo>;

export type PartialUpdateRestCarInfo = RestOf<PartialUpdateCarInfo>;

export type EntityResponseType = HttpResponse<ICarInfo>;
export type EntityArrayResponseType = HttpResponse<ICarInfo[]>;

@Injectable({ providedIn: 'root' })
export class CarInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/car-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(carInfo: NewCarInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carInfo);
    return this.http
      .post<RestCarInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(carInfo: ICarInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carInfo);
    return this.http
      .put<RestCarInfo>(`${this.resourceUrl}/${this.getCarInfoIdentifier(carInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(carInfo: PartialUpdateCarInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carInfo);
    return this.http
      .patch<RestCarInfo>(`${this.resourceUrl}/${this.getCarInfoIdentifier(carInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCarInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCarInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarInfoIdentifier(carInfo: Pick<ICarInfo, 'id'>): number {
    return carInfo.id;
  }

  compareCarInfo(o1: Pick<ICarInfo, 'id'> | null, o2: Pick<ICarInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarInfoIdentifier(o1) === this.getCarInfoIdentifier(o2) : o1 === o2;
  }

  addCarInfoToCollectionIfMissing<Type extends Pick<ICarInfo, 'id'>>(
    carInfoCollection: Type[],
    ...carInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const carInfos: Type[] = carInfosToCheck.filter(isPresent);
    if (carInfos.length > 0) {
      const carInfoCollectionIdentifiers = carInfoCollection.map(carInfoItem => this.getCarInfoIdentifier(carInfoItem)!);
      const carInfosToAdd = carInfos.filter(carInfoItem => {
        const carInfoIdentifier = this.getCarInfoIdentifier(carInfoItem);
        if (carInfoCollectionIdentifiers.includes(carInfoIdentifier)) {
          return false;
        }
        carInfoCollectionIdentifiers.push(carInfoIdentifier);
        return true;
      });
      return [...carInfosToAdd, ...carInfoCollection];
    }
    return carInfoCollection;
  }

  protected convertDateFromClient<T extends ICarInfo | NewCarInfo | PartialUpdateCarInfo>(carInfo: T): RestOf<T> {
    return {
      ...carInfo,
      purchaseDate: carInfo.purchaseDate?.format(DATE_FORMAT) ?? null,
      vehicleTaxValidityDate: carInfo.vehicleTaxValidityDate?.format(DATE_FORMAT) ?? null,
      originalInServiceDate: carInfo.originalInServiceDate?.format(DATE_FORMAT) ?? null,
      insuranceValidityDate: carInfo.insuranceValidityDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCarInfo: RestCarInfo): ICarInfo {
    return {
      ...restCarInfo,
      purchaseDate: restCarInfo.purchaseDate ? dayjs(restCarInfo.purchaseDate) : undefined,
      vehicleTaxValidityDate: restCarInfo.vehicleTaxValidityDate ? dayjs(restCarInfo.vehicleTaxValidityDate) : undefined,
      originalInServiceDate: restCarInfo.originalInServiceDate ? dayjs(restCarInfo.originalInServiceDate) : undefined,
      insuranceValidityDate: restCarInfo.insuranceValidityDate ? dayjs(restCarInfo.insuranceValidityDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCarInfo>): HttpResponse<ICarInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCarInfo[]>): HttpResponse<ICarInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
