import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonInfoComponent } from './list/person-info.component';
import { PersonInfoDetailComponent } from './detail/person-info-detail.component';
import { PersonInfoUpdateComponent } from './update/person-info-update.component';
import { PersonInfoDeleteDialogComponent } from './delete/person-info-delete-dialog.component';
import { PersonInfoRoutingModule } from './route/person-info-routing.module';

@NgModule({
  imports: [SharedModule, PersonInfoRoutingModule],
  declarations: [PersonInfoComponent, PersonInfoDetailComponent, PersonInfoUpdateComponent, PersonInfoDeleteDialogComponent],
})
export class PersonInfoModule {}
