import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonInfoComponent } from '../list/person-info.component';
import { PersonInfoDetailComponent } from '../detail/person-info-detail.component';
import { PersonInfoUpdateComponent } from '../update/person-info-update.component';
import { PersonInfoRoutingResolveService } from './person-info-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personInfoRoute: Routes = [
  {
    path: '',
    component: PersonInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonInfoDetailComponent,
    resolve: {
      personInfo: PersonInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonInfoUpdateComponent,
    resolve: {
      personInfo: PersonInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonInfoUpdateComponent,
    resolve: {
      personInfo: PersonInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personInfoRoute)],
  exports: [RouterModule],
})
export class PersonInfoRoutingModule {}
