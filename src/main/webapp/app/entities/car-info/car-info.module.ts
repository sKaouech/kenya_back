import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CarInfoComponent } from './list/car-info.component';
import { CarInfoDetailComponent } from './detail/car-info-detail.component';
import { CarInfoUpdateComponent } from './update/car-info-update.component';
import { CarInfoDeleteDialogComponent } from './delete/car-info-delete-dialog.component';
import { CarInfoRoutingModule } from './route/car-info-routing.module';

@NgModule({
  imports: [SharedModule, CarInfoRoutingModule],
  declarations: [CarInfoComponent, CarInfoDetailComponent, CarInfoUpdateComponent, CarInfoDeleteDialogComponent],
})
export class CarInfoModule {}
