import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person-info',
        data: { pageTitle: 'PersonInfos' },
        loadChildren: () => import('./person-info/person-info.module').then(m => m.PersonInfoModule),
      },
      {
        path: 'car-info',
        data: { pageTitle: 'CarInfos' },
        loadChildren: () => import('./car-info/car-info.module').then(m => m.CarInfoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
