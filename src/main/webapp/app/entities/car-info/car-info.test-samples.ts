import dayjs from 'dayjs/esm';

import { ICarInfo, NewCarInfo } from './car-info.model';

export const sampleWithRequiredData: ICarInfo = {
  id: 96895,
};

export const sampleWithPartialData: ICarInfo = {
  id: 795,
  purchaseDate: dayjs('2024-05-14'),
  golanFlagValidity: 'Chair green',
  vehicleTaxValidityDate: dayjs('2024-05-15'),
  technicalInspection: 'Bacon Program Ergonomic',
  firstOwner: 'Soap',
  firstOwnerID: 'synthesize Port',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithFullData: ICarInfo = {
  id: 72435,
  chassisNumber: 'Market scale blockchains',
  licensePlateNumber: 'Executive RAM Fresh',
  brand: 'Salad multi-byte South',
  purchaseDate: dayjs('2024-05-15'),
  golanFlagValidity: 'Avon Electronics',
  vehicleTaxValidityDate: dayjs('2024-05-15'),
  originalInServiceDate: dayjs('2024-05-15'),
  category: 'Kids',
  insuranceValidityDate: dayjs('2024-05-15'),
  technicalInspection: 'primary Auto',
  firstOwner: 'withdrawal',
  firstOwnerID: 'Soft',
  ownersID: 'Switchable info-mediaries',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  wanted: true,
};

export const sampleWithNewData: NewCarInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
