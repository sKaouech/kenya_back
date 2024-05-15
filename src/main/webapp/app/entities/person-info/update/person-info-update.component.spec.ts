import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonInfoFormService } from './person-info-form.service';
import { PersonInfoService } from '../service/person-info.service';
import { IPersonInfo } from '../person-info.model';

import { PersonInfoUpdateComponent } from './person-info-update.component';

describe('PersonInfo Management Update Component', () => {
  let comp: PersonInfoUpdateComponent;
  let fixture: ComponentFixture<PersonInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personInfoFormService: PersonInfoFormService;
  let personInfoService: PersonInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonInfoUpdateComponent],
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
      .overrideTemplate(PersonInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personInfoFormService = TestBed.inject(PersonInfoFormService);
    personInfoService = TestBed.inject(PersonInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personInfo: IPersonInfo = { id: 456 };

      activatedRoute.data = of({ personInfo });
      comp.ngOnInit();

      expect(comp.personInfo).toEqual(personInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInfo>>();
      const personInfo = { id: 123 };
      jest.spyOn(personInfoFormService, 'getPersonInfo').mockReturnValue(personInfo);
      jest.spyOn(personInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInfo }));
      saveSubject.complete();

      // THEN
      expect(personInfoFormService.getPersonInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personInfoService.update).toHaveBeenCalledWith(expect.objectContaining(personInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInfo>>();
      const personInfo = { id: 123 };
      jest.spyOn(personInfoFormService, 'getPersonInfo').mockReturnValue({ id: null });
      jest.spyOn(personInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personInfo }));
      saveSubject.complete();

      // THEN
      expect(personInfoFormService.getPersonInfo).toHaveBeenCalled();
      expect(personInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonInfo>>();
      const personInfo = { id: 123 };
      jest.spyOn(personInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personInfoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
