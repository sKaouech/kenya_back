import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PersonInfoFormService, PersonInfoFormGroup } from './person-info-form.service';
import { IPersonInfo } from '../person-info.model';
import { PersonInfoService } from '../service/person-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-person-info-update',
  templateUrl: './person-info-update.component.html',
})
export class PersonInfoUpdateComponent implements OnInit {
  isSaving = false;
  personInfo: IPersonInfo | null = null;

  editForm: PersonInfoFormGroup = this.personInfoFormService.createPersonInfoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected personInfoService: PersonInfoService,
    protected personInfoFormService: PersonInfoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInfo }) => {
      this.personInfo = personInfo;
      if (personInfo) {
        this.updateForm(personInfo);
      }
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
    const personInfo = this.personInfoFormService.getPersonInfo(this.editForm);
    if (personInfo.id !== null) {
      this.subscribeToSaveResponse(this.personInfoService.update(personInfo));
    } else {
      this.subscribeToSaveResponse(this.personInfoService.create(personInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInfo>>): void {
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

  protected updateForm(personInfo: IPersonInfo): void {
    this.personInfo = personInfo;
    this.personInfoFormService.resetForm(this.editForm, personInfo);
  }
}
