import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CarInfoFormService, CarInfoFormGroup } from './car-info-form.service';
import { ICarInfo } from '../car-info.model';
import { CarInfoService } from '../service/car-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPersonInfo } from 'app/entities/person-info/person-info.model';
import { PersonInfoService } from 'app/entities/person-info/service/person-info.service';

@Component({
  selector: 'jhi-car-info-update',
  templateUrl: './car-info-update.component.html',
})
export class CarInfoUpdateComponent implements OnInit {
  isSaving = false;
  carInfo: ICarInfo | null = null;

  personInfosSharedCollection: IPersonInfo[] = [];

  editForm: CarInfoFormGroup = this.carInfoFormService.createCarInfoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected carInfoService: CarInfoService,
    protected carInfoFormService: CarInfoFormService,
    protected personInfoService: PersonInfoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePersonInfo = (o1: IPersonInfo | null, o2: IPersonInfo | null): boolean => this.personInfoService.comparePersonInfo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carInfo }) => {
      this.carInfo = carInfo;
      if (carInfo) {
        this.updateForm(carInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('jhipExpApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carInfo = this.carInfoFormService.getCarInfo(this.editForm);
    if (carInfo.id !== null) {
      this.subscribeToSaveResponse(this.carInfoService.update(carInfo));
    } else {
      this.subscribeToSaveResponse(this.carInfoService.create(carInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarInfo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(carInfo: ICarInfo): void {
    this.carInfo = carInfo;
    this.carInfoFormService.resetForm(this.editForm, carInfo);

    this.personInfosSharedCollection = this.personInfoService.addPersonInfoToCollectionIfMissing<IPersonInfo>(
      this.personInfosSharedCollection,
      carInfo.personInfo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.personInfoService
      .query()
      .pipe(map((res: HttpResponse<IPersonInfo[]>) => res.body ?? []))
      .pipe(
        map((personInfos: IPersonInfo[]) =>
          this.personInfoService.addPersonInfoToCollectionIfMissing<IPersonInfo>(personInfos, this.carInfo?.personInfo)
        )
      )
      .subscribe((personInfos: IPersonInfo[]) => (this.personInfosSharedCollection = personInfos));
  }
}
