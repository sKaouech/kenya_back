import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarInfo } from '../car-info.model';
import { CarInfoService } from '../service/car-info.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './car-info-delete-dialog.component.html',
})
export class CarInfoDeleteDialogComponent {
  carInfo?: ICarInfo;

  constructor(protected carInfoService: CarInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
