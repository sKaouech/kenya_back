import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person-info.test-samples';

import { PersonInfoFormService } from './person-info-form.service';

describe('PersonInfo Form Service', () => {
  let service: PersonInfoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonInfoFormService);
  });

  describe('Service methods', () => {
    describe('createPersonInfoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonInfoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nationalID: expect.any(Object),
            identityType: expect.any(Object),
            passportID: expect.any(Object),
            driveLicenceID: expect.any(Object),
            driveLicenceType: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            nationalIdIssueDate: expect.any(Object),
            birthDate: expect.any(Object),
            passportIssueDate: expect.any(Object),
            driveLicenceIssueDate: expect.any(Object),
            job: expect.any(Object),
            fathersFirstName: expect.any(Object),
            motherFirstName: expect.any(Object),
            photo: expect.any(Object),
            wanted: expect.any(Object),
          })
        );
      });

      it('passing IPersonInfo should create a new form with FormGroup', () => {
        const formGroup = service.createPersonInfoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nationalID: expect.any(Object),
            identityType: expect.any(Object),
            passportID: expect.any(Object),
            driveLicenceID: expect.any(Object),
            driveLicenceType: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            nationalIdIssueDate: expect.any(Object),
            birthDate: expect.any(Object),
            passportIssueDate: expect.any(Object),
            driveLicenceIssueDate: expect.any(Object),
            job: expect.any(Object),
            fathersFirstName: expect.any(Object),
            motherFirstName: expect.any(Object),
            photo: expect.any(Object),
            wanted: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonInfo', () => {
      it('should return NewPersonInfo for default PersonInfo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonInfoFormGroup(sampleWithNewData);

        const personInfo = service.getPersonInfo(formGroup) as any;

        expect(personInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonInfo for empty PersonInfo initial value', () => {
        const formGroup = service.createPersonInfoFormGroup();

        const personInfo = service.getPersonInfo(formGroup) as any;

        expect(personInfo).toMatchObject({});
      });

      it('should return IPersonInfo', () => {
        const formGroup = service.createPersonInfoFormGroup(sampleWithRequiredData);

        const personInfo = service.getPersonInfo(formGroup) as any;

        expect(personInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonInfo should not enable id FormControl', () => {
        const formGroup = service.createPersonInfoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonInfo should disable id FormControl', () => {
        const formGroup = service.createPersonInfoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
