import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubsector, Subsector } from 'app/shared/model/subsector.model';
import { SubsectorService } from './subsector.service';
import { SubsectorComponent } from './subsector.component';
import { SubsectorDetailComponent } from './subsector-detail.component';
import { SubsectorUpdateComponent } from './subsector-update.component';

@Injectable({ providedIn: 'root' })
export class SubsectorResolve implements Resolve<ISubsector> {
  constructor(private service: SubsectorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubsector> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subsector: HttpResponse<Subsector>) => {
          if (subsector.body) {
            return of(subsector.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Subsector());
  }
}

export const subsectorRoute: Routes = [
  {
    path: '',
    component: SubsectorComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.subsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubsectorDetailComponent,
    resolve: {
      subsector: SubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.subsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubsectorUpdateComponent,
    resolve: {
      subsector: SubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.subsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubsectorUpdateComponent,
    resolve: {
      subsector: SubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.subsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
