import dayjs from 'dayjs/esm';
import { IPersonInfo } from 'app/entities/person-info/person-info.model';

export interface ICarInfo {
  id: number;
  chassisNumber?: string | null;
  licensePlateNumber?: string | null;
  brand?: string | null;
  purchaseDate?: dayjs.Dayjs | null;
  golanFlagValidity?: string | null;
  vehicleTaxValidityDate?: dayjs.Dayjs | null;
  originalInServiceDate?: dayjs.Dayjs | null;
  category?: string | null;
  insuranceValidityDate?: dayjs.Dayjs | null;
  technicalInspection?: string | null;
  firstOwner?: string | null;
  firstOwnerID?: string | null;
  ownersID?: string | null;
  ownersName?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  wanted?: boolean | null;
  personInfo?: Pick<IPersonInfo, 'id'> | null;
}

export type NewCarInfo = Omit<ICarInfo, 'id'> & { id: null };
