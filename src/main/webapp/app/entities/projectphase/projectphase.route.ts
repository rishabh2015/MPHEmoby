import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjectphase, Projectphase } from 'app/shared/model/projectphase.model';
import { ProjectphaseService } from './projectphase.service';
import { ProjectphaseComponent } from './projectphase.component';
import { ProjectphaseDetailComponent } from './projectphase-detail.component';
import { ProjectphaseUpdateComponent } from './projectphase-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectphaseResolve implements Resolve<IProjectphase> {
  constructor(private service: ProjectphaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectphase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projectphase: HttpResponse<Projectphase>) => {
          if (projectphase.body) {
            return of(projectphase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Projectphase());
  }
}

export const projectphaseRoute: Routes = [
  {
    path: '',
    component: ProjectphaseComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectphaseDetailComponent,
    resolve: {
      projectphase: ProjectphaseResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectphaseUpdateComponent,
    resolve: {
      projectphase: ProjectphaseResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectphaseUpdateComponent,
    resolve: {
      projectphase: ProjectphaseResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphase.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
