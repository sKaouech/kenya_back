import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonInfo } from '../person-info.model';
import { PersonInfoService } from '../service/person-info.service';

@Injectable({ providedIn: 'root' })
export class PersonInfoRoutingResolveService implements Resolve<IPersonInfo | null> {
  constructor(protected service: PersonInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonInfo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personInfo: HttpResponse<IPersonInfo>) => {
          if (personInfo.body) {
            return of(personInfo.body);
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
