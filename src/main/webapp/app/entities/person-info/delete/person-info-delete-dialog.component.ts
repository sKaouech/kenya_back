import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonInfo } from '../person-info.model';
import { PersonInfoService } from '../service/person-info.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './person-info-delete-dialog.component.html',
})
export class PersonInfoDeleteDialogComponent {
  personInfo?: IPersonInfo;

  constructor(protected personInfoService: PersonInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
