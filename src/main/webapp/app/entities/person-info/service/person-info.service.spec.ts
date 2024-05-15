import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPersonInfo } from '../person-info.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../person-info.test-samples';

import { PersonInfoService, RestPersonInfo } from './person-info.service';

const requireRestSample: RestPersonInfo = {
  ...sampleWithRequiredData,
  nationalIdIssueDate: sampleWithRequiredData.nationalIdIssueDate?.format(DATE_FORMAT),
  passportIssueDate: sampleWithRequiredData.passportIssueDate?.format(DATE_FORMAT),
  driveLicenceIssueDate: sampleWithRequiredData.driveLicenceIssueDate?.format(DATE_FORMAT),
};

describe('PersonInfo Service', () => {
  let service: PersonInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonInfo | IPersonInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonInfoService);
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

    it('should create a PersonInfo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonInfo', () => {
      const personInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonInfo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonInfo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonInfoToCollectionIfMissing', () => {
      it('should add a PersonInfo to an empty array', () => {
        const personInfo: IPersonInfo = sampleWithRequiredData;
        expectedResult = service.addPersonInfoToCollectionIfMissing([], personInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInfo);
      });

      it('should not add a PersonInfo to an array that contains it', () => {
        const personInfo: IPersonInfo = sampleWithRequiredData;
        const personInfoCollection: IPersonInfo[] = [
          {
            ...personInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonInfoToCollectionIfMissing(personInfoCollection, personInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonInfo to an array that doesn't contain it", () => {
        const personInfo: IPersonInfo = sampleWithRequiredData;
        const personInfoCollection: IPersonInfo[] = [sampleWithPartialData];
        expectedResult = service.addPersonInfoToCollectionIfMissing(personInfoCollection, personInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInfo);
      });

      it('should add only unique PersonInfo to an array', () => {
        const personInfoArray: IPersonInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personInfoCollection: IPersonInfo[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInfoToCollectionIfMissing(personInfoCollection, ...personInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personInfo: IPersonInfo = sampleWithRequiredData;
        const personInfo2: IPersonInfo = sampleWithPartialData;
        expectedResult = service.addPersonInfoToCollectionIfMissing([], personInfo, personInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personInfo);
        expect(expectedResult).toContain(personInfo2);
      });

      it('should accept null and undefined values', () => {
        const personInfo: IPersonInfo = sampleWithRequiredData;
        expectedResult = service.addPersonInfoToCollectionIfMissing([], null, personInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personInfo);
      });

      it('should return initial array if no PersonInfo is added', () => {
        const personInfoCollection: IPersonInfo[] = [sampleWithRequiredData];
        expectedResult = service.addPersonInfoToCollectionIfMissing(personInfoCollection, undefined, null);
        expect(expectedResult).toEqual(personInfoCollection);
      });
    });

    describe('comparePersonInfo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonInfo(entity1, entity2);
        const compareResult2 = service.comparePersonInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonInfo(entity1, entity2);
        const compareResult2 = service.comparePersonInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonInfo(entity1, entity2);
        const compareResult2 = service.comparePersonInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
