import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjectphaseActivity, ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from './projectphase-activity.service';
import { ProjectphaseActivityComponent } from './projectphase-activity.component';
import { ProjectphaseActivityDetailComponent } from './projectphase-activity-detail.component';
import { ProjectphaseActivityUpdateComponent } from './projectphase-activity-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectphaseActivityResolve implements Resolve<IProjectphaseActivity> {
  constructor(private service: ProjectphaseActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectphaseActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projectphaseActivity: HttpResponse<ProjectphaseActivity>) => {
          if (projectphaseActivity.body) {
            return of(projectphaseActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectphaseActivity());
  }
}

export const projectphaseActivityRoute: Routes = [
  {
    path: '',
    component: ProjectphaseActivityComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphaseActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectphaseActivityDetailComponent,
    resolve: {
      projectphaseActivity: ProjectphaseActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphaseActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectphaseActivityUpdateComponent,
    resolve: {
      projectphaseActivity: ProjectphaseActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphaseActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectphaseActivityUpdateComponent,
    resolve: {
      projectphaseActivity: ProjectphaseActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.projectphaseActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
