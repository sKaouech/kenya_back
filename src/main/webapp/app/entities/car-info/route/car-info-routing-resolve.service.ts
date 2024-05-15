import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarInfo } from '../car-info.model';
import { CarInfoService } from '../service/car-info.service';

@Injectable({ providedIn: 'root' })
export class CarInfoRoutingResolveService implements Resolve<ICarInfo | null> {
  constructor(protected service: CarInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarInfo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((carInfo: HttpResponse<ICarInfo>) => {
          if (carInfo.body) {
            return of(carInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
