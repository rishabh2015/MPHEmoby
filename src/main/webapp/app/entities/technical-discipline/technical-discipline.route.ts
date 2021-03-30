import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITechnicalDiscipline, TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from './technical-discipline.service';
import { TechnicalDisciplineComponent } from './technical-discipline.component';
import { TechnicalDisciplineDetailComponent } from './technical-discipline-detail.component';
import { TechnicalDisciplineUpdateComponent } from './technical-discipline-update.component';

@Injectable({ providedIn: 'root' })
export class TechnicalDisciplineResolve implements Resolve<ITechnicalDiscipline> {
  constructor(private service: TechnicalDisciplineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITechnicalDiscipline> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((technicalDiscipline: HttpResponse<TechnicalDiscipline>) => {
          if (technicalDiscipline.body) {
            return of(technicalDiscipline.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TechnicalDiscipline());
  }
}

export const technicalDisciplineRoute: Routes = [
  {
    path: '',
    component: TechnicalDisciplineComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.technicalDiscipline.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TechnicalDisciplineDetailComponent,
    resolve: {
      technicalDiscipline: TechnicalDisciplineResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.technicalDiscipline.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TechnicalDisciplineUpdateComponent,
    resolve: {
      technicalDiscipline: TechnicalDisciplineResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.technicalDiscipline.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TechnicalDisciplineUpdateComponent,
    resolve: {
      technicalDiscipline: TechnicalDisciplineResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.technicalDiscipline.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
