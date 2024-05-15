import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CarInfoFormService } from './car-info-form.service';
import { CarInfoService } from '../service/car-info.service';
import { ICarInfo } from '../car-info.model';
import { IPersonInfo } from 'app/entities/person-info/person-info.model';
import { PersonInfoService } from 'app/entities/person-info/service/person-info.service';

import { CarInfoUpdateComponent } from './car-info-update.component';

describe('CarInfo Management Update Component', () => {
  let comp: CarInfoUpdateComponent;
  let fixture: ComponentFixture<CarInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carInfoFormService: CarInfoFormService;
  let carInfoService: CarInfoService;
  let personInfoService: PersonInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CarInfoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CarInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carInfoFormService = TestBed.inject(CarInfoFormService);
    carInfoService = TestBed.inject(CarInfoService);
    personInfoService = TestBed.inject(PersonInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PersonInfo query and add missing value', () => {
      const carInfo: ICarInfo = { id: 456 };
      const personInfo: IPersonInfo = { id: 97595 };
      carInfo.personInfo = personInfo;

      const personInfoCollection: IPersonInfo[] = [{ id: 55787 }];
      jest.spyOn(personInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: personInfoCollection })));
      const additionalPersonInfos = [personInfo];
      const expectedCollection: IPersonInfo[] = [...additionalPersonInfos, ...personInfoCollection];
      jest.spyOn(personInfoService, 'addPersonInfoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carInfo });
      comp.ngOnInit();

      expect(personInfoService.query).toHaveBeenCalled();
      expect(personInfoService.addPersonInfoToCollectionIfMissing).toHaveBeenCalledWith(
        personInfoCollection,
        ...additionalPersonInfos.map(expect.objectContaining)
      );
      expect(comp.personInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const carInfo: ICarInfo = { id: 456 };
      const personInfo: IPersonInfo = { id: 37606 };
      carInfo.personInfo = personInfo;

      activatedRoute.data = of({ carInfo });
      comp.ngOnInit();

      expect(comp.personInfosSharedCollection).toContain(personInfo);
      expect(comp.carInfo).toEqual(carInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarInfo>>();
      const carInfo = { id: 123 };
      jest.spyOn(carInfoFormService, 'getCarInfo').mockReturnValue(carInfo);
      jest.spyOn(carInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carInfo }));
      saveSubject.complete();

      // THEN
      expect(carInfoFormService.getCarInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(carInfoService.update).toHaveBeenCalledWith(expect.objectContaining(carInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarInfo>>();
      const carInfo = { id: 123 };
      jest.spyOn(carInfoFormService, 'getCarInfo').mockReturnValue({ id: null });
      jest.spyOn(carInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carInfo }));
      saveSubject.complete();

      // THEN
      expect(carInfoFormService.getCarInfo).toHaveBeenCalled();
      expect(carInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarInfo>>();
      const carInfo = { id: 123 };
      jest.spyOn(carInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carInfoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePersonInfo', () => {
      it('Should forward to personInfoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personInfoService, 'comparePersonInfo');
        comp.comparePersonInfo(entity, entity2);
        expect(personInfoService.comparePersonInfo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
