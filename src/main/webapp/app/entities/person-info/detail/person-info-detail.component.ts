import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonInfo } from '../person-info.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-person-info-detail',
  templateUrl: './person-info-detail.component.html',
})
export class PersonInfoDetailComponent implements OnInit {
  personInfo: IPersonInfo | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personInfo }) => {
      this.personInfo = personInfo;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
