import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICarInfo } from '../car-info.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../car-info.test-samples';

import { CarInfoService, RestCarInfo } from './car-info.service';

const requireRestSample: RestCarInfo = {
  ...sampleWithRequiredData,
  purchaseDate: sampleWithRequiredData.purchaseDate?.format(DATE_FORMAT),
  vehicleTaxValidityDate: sampleWithRequiredData.vehicleTaxValidityDate?.format(DATE_FORMAT),
  originalInServiceDate: sampleWithRequiredData.originalInServiceDate?.format(DATE_FORMAT),
  insuranceValidityDate: sampleWithRequiredData.insuranceValidityDate?.format(DATE_FORMAT),
};

describe('CarInfo Service', () => {
  let service: CarInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarInfo | ICarInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarInfoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CarInfo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const carInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CarInfo', () => {
      const carInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CarInfo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CarInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CarInfo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarInfoToCollectionIfMissing', () => {
      it('should add a CarInfo to an empty array', () => {
        const carInfo: ICarInfo = sampleWithRequiredData;
        expectedResult = service.addCarInfoToCollectionIfMissing([], carInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carInfo);
      });

      it('should not add a CarInfo to an array that contains it', () => {
        const carInfo: ICarInfo = sampleWithRequiredData;
        const carInfoCollection: ICarInfo[] = [
          {
            ...carInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarInfoToCollectionIfMissing(carInfoCollection, carInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CarInfo to an array that doesn't contain it", () => {
        const carInfo: ICarInfo = sampleWithRequiredData;
        const carInfoCollection: ICarInfo[] = [sampleWithPartialData];
        expectedResult = service.addCarInfoToCollectionIfMissing(carInfoCollection, carInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carInfo);
      });

      it('should add only unique CarInfo to an array', () => {
        const carInfoArray: ICarInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carInfoCollection: ICarInfo[] = [sampleWithRequiredData];
        expectedResult = service.addCarInfoToCollectionIfMissing(carInfoCollection, ...carInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carInfo: ICarInfo = sampleWithRequiredData;
        const carInfo2: ICarInfo = sampleWithPartialData;
        expectedResult = service.addCarInfoToCollectionIfMissing([], carInfo, carInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carInfo);
        expect(expectedResult).toContain(carInfo2);
      });

      it('should accept null and undefined values', () => {
        const carInfo: ICarInfo = sampleWithRequiredData;
        expectedResult = service.addCarInfoToCollectionIfMissing([], null, carInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carInfo);
      });

      it('should return initial array if no CarInfo is added', () => {
        const carInfoCollection: ICarInfo[] = [sampleWithRequiredData];
        expectedResult = service.addCarInfoToCollectionIfMissing(carInfoCollection, undefined, null);
        expect(expectedResult).toEqual(carInfoCollection);
      });
    });

    describe('compareCarInfo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCarInfo(entity1, entity2);
        const compareResult2 = service.compareCarInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCarInfo(entity1, entity2);
        const compareResult2 = service.compareCarInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCarInfo(entity1, entity2);
        const compareResult2 = service.compareCarInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
