import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICarInfo, NewCarInfo } from '../car-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICarInfo for edit and NewCarInfoFormGroupInput for create.
 */
type CarInfoFormGroupInput = ICarInfo | PartialWithRequiredKeyOf<NewCarInfo>;

type CarInfoFormDefaults = Pick<NewCarInfo, 'id' | 'wanted'>;

type CarInfoFormGroupContent = {
  id: FormControl<ICarInfo['id'] | NewCarInfo['id']>;
  chassisNumber: FormControl<ICarInfo['chassisNumber']>;
  licensePlateNumber: FormControl<ICarInfo['licensePlateNumber']>;
  brand: FormControl<ICarInfo['brand']>;
  purchaseDate: FormControl<ICarInfo['purchaseDate']>;
  golanFlagValidity: FormControl<ICarInfo['golanFlagValidity']>;
  vehicleTaxValidityDate: FormControl<ICarInfo['vehicleTaxValidityDate']>;
  originalInServiceDate: FormControl<ICarInfo['originalInServiceDate']>;
  category: FormControl<ICarInfo['category']>;
  insuranceValidityDate: FormControl<ICarInfo['insuranceValidityDate']>;
  technicalInspection: FormControl<ICarInfo['technicalInspection']>;
  firstOwner: FormControl<ICarInfo['firstOwner']>;
  firstOwnerID: FormControl<ICarInfo['firstOwnerID']>;
  ownersID: FormControl<ICarInfo['ownersID']>;
  ownersName: FormControl<ICarInfo['ownersName']>;
  photo: FormControl<ICarInfo['photo']>;
  photoContentType: FormControl<ICarInfo['photoContentType']>;
  wanted: FormControl<ICarInfo['wanted']>;
  personInfo: FormControl<ICarInfo['personInfo']>;
};

export type CarInfoFormGroup = FormGroup<CarInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CarInfoFormService {
  createCarInfoFormGroup(carInfo: CarInfoFormGroupInput = { id: null }): CarInfoFormGroup {
    const carInfoRawValue = {
      ...this.getFormDefaults(),
      ...carInfo,
    };
    return new FormGroup<CarInfoFormGroupContent>({
      id: new FormControl(
        { value: carInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chassisNumber: new FormControl(carInfoRawValue.chassisNumber),
      licensePlateNumber: new FormControl(carInfoRawValue.licensePlateNumber),
      brand: new FormControl(carInfoRawValue.brand),
      purchaseDate: new FormControl(carInfoRawValue.purchaseDate),
      golanFlagValidity: new FormControl(carInfoRawValue.golanFlagValidity),
      vehicleTaxValidityDate: new FormControl(carInfoRawValue.vehicleTaxValidityDate),
      originalInServiceDate: new FormControl(carInfoRawValue.originalInServiceDate),
      category: new FormControl(carInfoRawValue.category),
      insuranceValidityDate: new FormControl(carInfoRawValue.insuranceValidityDate),
      technicalInspection: new FormControl(carInfoRawValue.technicalInspection),
      firstOwner: new FormControl(carInfoRawValue.firstOwner),
      firstOwnerID: new FormControl(carInfoRawValue.firstOwnerID),
      ownersID: new FormControl(carInfoRawValue.ownersID),
      ownersName: new FormControl(carInfoRawValue.ownersName),
      photo: new FormControl(carInfoRawValue.photo),
      photoContentType: new FormControl(carInfoRawValue.photoContentType),
      wanted: new FormControl(carInfoRawValue.wanted),
      personInfo: new FormControl(carInfoRawValue.personInfo),
    });
  }

  getCarInfo(form: CarInfoFormGroup): ICarInfo | NewCarInfo {
    return form.getRawValue() as ICarInfo | NewCarInfo;
  }

  resetForm(form: CarInfoFormGroup, carInfo: CarInfoFormGroupInput): void {
    const carInfoRawValue = { ...this.getFormDefaults(), ...carInfo };
    form.reset(
      {
        ...carInfoRawValue,
        id: { value: carInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CarInfoFormDefaults {
    return {
      id: null,
      wanted: false,
    };
  }
}
