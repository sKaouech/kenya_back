import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CarInfoComponent } from '../list/car-info.component';
import { CarInfoDetailComponent } from '../detail/car-info-detail.component';
import { CarInfoUpdateComponent } from '../update/car-info-update.component';
import { CarInfoRoutingResolveService } from './car-info-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const carInfoRoute: Routes = [
  {
    path: '',
    component: CarInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarInfoDetailComponent,
    resolve: {
      carInfo: CarInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarInfoUpdateComponent,
    resolve: {
      carInfo: CarInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarInfoUpdateComponent,
    resolve: {
      carInfo: CarInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(carInfoRoute)],
  exports: [RouterModule],
})
export class CarInfoRoutingModule {}
