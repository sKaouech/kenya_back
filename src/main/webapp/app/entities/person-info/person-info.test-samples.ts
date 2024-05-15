import dayjs from 'dayjs/esm';

import { IPersonInfo, NewPersonInfo } from './person-info.model';

export const sampleWithRequiredData: IPersonInfo = {
  id: 43257,
};

export const sampleWithPartialData: IPersonInfo = {
  id: 66013,
  nationalID: 'Directives Indiana',
  passportID: 'system',
  driveLicenceID: 'payment engineer Tuna',
  nationalIdIssueDate: dayjs('2024-05-15'),
  driveLicenceIssueDate: dayjs('2024-05-15'),
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
};

export const sampleWithFullData: IPersonInfo = {
  id: 98846,
  nationalID: 'Gold Kids Auto',
  identityType: 'relationships deposit CSS',
  passportID: 'Buckinghamshire',
  driveLicenceID: 'East',
  driveLicenceType: 'intuitive',
  firstName: 'Jakob',
  lastName: 'Collier',
  nationalIdIssueDate: dayjs('2024-05-14'),
  birthDate: 'matrices technologies',
  passportIssueDate: dayjs('2024-05-14'),
  driveLicenceIssueDate: dayjs('2024-05-14'),
  job: 'Group quantifying',
  fathersFirstName: 'COM Business-focused',
  motherFirstName: 'Rhode',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  wanted: false,
};

export const sampleWithNewData: NewPersonInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
