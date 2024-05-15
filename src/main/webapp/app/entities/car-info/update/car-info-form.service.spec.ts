import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../car-info.test-samples';

import { CarInfoFormService } from './car-info-form.service';

describe('CarInfo Form Service', () => {
  let service: CarInfoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarInfoFormService);
  });

  describe('Service methods', () => {
    describe('createCarInfoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCarInfoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chassisNumber: expect.any(Object),
            licensePlateNumber: expect.any(Object),
            brand: expect.any(Object),
            purchaseDate: expect.any(Object),
            golanFlagValidity: expect.any(Object),
            vehicleTaxValidityDate: expect.any(Object),
            originalInServiceDate: expect.any(Object),
            category: expect.any(Object),
            insuranceValidityDate: expect.any(Object),
            technicalInspection: expect.any(Object),
            firstOwner: expect.any(Object),
            firstOwnerID: expect.any(Object),
            ownersID: expect.any(Object),
            photo: expect.any(Object),
            wanted: expect.any(Object),
            personInfo: expect.any(Object),
          })
        );
      });

      it('passing ICarInfo should create a new form with FormGroup', () => {
        const formGroup = service.createCarInfoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            chassisNumber: expect.any(Object),
            licensePlateNumber: expect.any(Object),
            brand: expect.any(Object),
            purchaseDate: expect.any(Object),
            golanFlagValidity: expect.any(Object),
            vehicleTaxValidityDate: expect.any(Object),
            originalInServiceDate: expect.any(Object),
            category: expect.any(Object),
            insuranceValidityDate: expect.any(Object),
            technicalInspection: expect.any(Object),
            firstOwner: expect.any(Object),
            firstOwnerID: expect.any(Object),
            ownersID: expect.any(Object),
            photo: expect.any(Object),
            wanted: expect.any(Object),
            personInfo: expect.any(Object),
          })
        );
      });
    });

    describe('getCarInfo', () => {
      it('should return NewCarInfo for default CarInfo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCarInfoFormGroup(sampleWithNewData);

        const carInfo = service.getCarInfo(formGroup) as any;

        expect(carInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewCarInfo for empty CarInfo initial value', () => {
        const formGroup = service.createCarInfoFormGroup();

        const carInfo = service.getCarInfo(formGroup) as any;

        expect(carInfo).toMatchObject({});
      });

      it('should return ICarInfo', () => {
        const formGroup = service.createCarInfoFormGroup(sampleWithRequiredData);

        const carInfo = service.getCarInfo(formGroup) as any;

        expect(carInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICarInfo should not enable id FormControl', () => {
        const formGroup = service.createCarInfoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCarInfo should disable id FormControl', () => {
        const formGroup = service.createCarInfoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
