import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonInfo, NewPersonInfo } from '../person-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonInfo for edit and NewPersonInfoFormGroupInput for create.
 */
type PersonInfoFormGroupInput = IPersonInfo | PartialWithRequiredKeyOf<NewPersonInfo>;

type PersonInfoFormDefaults = Pick<NewPersonInfo, 'id' | 'wanted'>;

type PersonInfoFormGroupContent = {
  id: FormControl<IPersonInfo['id'] | NewPersonInfo['id']>;
  nationalID: FormControl<IPersonInfo['nationalID']>;
  identityType: FormControl<IPersonInfo['identityType']>;
  passportID: FormControl<IPersonInfo['passportID']>;
  driveLicenceID: FormControl<IPersonInfo['driveLicenceID']>;
  driveLicenceType: FormControl<IPersonInfo['driveLicenceType']>;
  firstName: FormControl<IPersonInfo['firstName']>;
  lastName: FormControl<IPersonInfo['lastName']>;
  nationalIdIssueDate: FormControl<IPersonInfo['nationalIdIssueDate']>;
  birthDate: FormControl<IPersonInfo['birthDate']>;
  passportIssueDate: FormControl<IPersonInfo['passportIssueDate']>;
  driveLicenceIssueDate: FormControl<IPersonInfo['driveLicenceIssueDate']>;
  job: FormControl<IPersonInfo['job']>;
  fathersFirstName: FormControl<IPersonInfo['fathersFirstName']>;
  motherFirstName: FormControl<IPersonInfo['motherFirstName']>;
  photo: FormControl<IPersonInfo['photo']>;
  photoContentType: FormControl<IPersonInfo['photoContentType']>;
  wanted: FormControl<IPersonInfo['wanted']>;
};

export type PersonInfoFormGroup = FormGroup<PersonInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonInfoFormService {
  createPersonInfoFormGroup(personInfo: PersonInfoFormGroupInput = { id: null }): PersonInfoFormGroup {
    const personInfoRawValue = {
      ...this.getFormDefaults(),
      ...personInfo,
    };
    return new FormGroup<PersonInfoFormGroupContent>({
      id: new FormControl(
        { value: personInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nationalID: new FormControl(personInfoRawValue.nationalID),
      identityType: new FormControl(personInfoRawValue.identityType),
      passportID: new FormControl(personInfoRawValue.passportID),
      driveLicenceID: new FormControl(personInfoRawValue.driveLicenceID),
      driveLicenceType: new FormControl(personInfoRawValue.driveLicenceType),
      firstName: new FormControl(personInfoRawValue.firstName),
      lastName: new FormControl(personInfoRawValue.lastName),
      nationalIdIssueDate: new FormControl(personInfoRawValue.nationalIdIssueDate),
      birthDate: new FormControl(personInfoRawValue.birthDate),
      passportIssueDate: new FormControl(personInfoRawValue.passportIssueDate),
      driveLicenceIssueDate: new FormControl(personInfoRawValue.driveLicenceIssueDate),
      job: new FormControl(personInfoRawValue.job),
      fathersFirstName: new FormControl(personInfoRawValue.fathersFirstName),
      motherFirstName: new FormControl(personInfoRawValue.motherFirstName),
      photo: new FormControl(personInfoRawValue.photo),
      photoContentType: new FormControl(personInfoRawValue.photoContentType),
      wanted: new FormControl(personInfoRawValue.wanted),
    });
  }

  getPersonInfo(form: PersonInfoFormGroup): IPersonInfo | NewPersonInfo {
    return form.getRawValue() as IPersonInfo | NewPersonInfo;
  }

  resetForm(form: PersonInfoFormGroup, personInfo: PersonInfoFormGroupInput): void {
    const personInfoRawValue = { ...this.getFormDefaults(), ...personInfo };
    form.reset(
      {
        ...personInfoRawValue,
        id: { value: personInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonInfoFormDefaults {
    return {
      id: null,
      wanted: false,
    };
  }
}
