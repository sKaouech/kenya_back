import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarInfo } from '../car-info.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-car-info-detail',
  templateUrl: './car-info-detail.component.html',
})
export class CarInfoDetailComponent implements OnInit {
  carInfo: ICarInfo | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carInfo }) => {
      this.carInfo = carInfo;
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
