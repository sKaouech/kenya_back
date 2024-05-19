import dayjs from 'dayjs/esm';

export interface IPersonInfo {
  id: number;
  nationalID?: string | null;
  identityType?: string | null;
  passportID?: string | null;
  driveLicenceID?: string | null;
  driveLicenceType?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  nationalIdIssueDate?: dayjs.Dayjs | null;
  birthDate?: string | null;
  passportIssueDate?: dayjs.Dayjs | null;
  driveLicenceIssueDate?: dayjs.Dayjs | null;
  job?: string | null;
  fathersFirstName?: string | null;
  motherFirstName?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  address?: string | null;
  wanted?: boolean | null;
}

export type NewPersonInfo = Omit<IPersonInfo, 'id'> & { id: null };
