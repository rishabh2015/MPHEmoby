import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobOpening, JobOpening } from 'app/shared/model/job-opening.model';
import { JobOpeningService } from './job-opening.service';
import { JobOpeningComponent } from './job-opening.component';
import { JobOpeningDetailComponent } from './job-opening-detail.component';
import { JobOpeningUpdateComponent } from './job-opening-update.component';

@Injectable({ providedIn: 'root' })
export class JobOpeningResolve implements Resolve<IJobOpening> {
  constructor(private service: JobOpeningService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobOpening> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobOpening: HttpResponse<JobOpening>) => {
          if (jobOpening.body) {
            return of(jobOpening.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobOpening());
  }
}

export const jobOpeningRoute: Routes = [
  {
    path: '',
    component: JobOpeningComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.jobOpening.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobOpeningDetailComponent,
    resolve: {
      jobOpening: JobOpeningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.jobOpening.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobOpeningUpdateComponent,
    resolve: {
      jobOpening: JobOpeningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.jobOpening.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobOpeningUpdateComponent,
    resolve: {
      jobOpening: JobOpeningResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.jobOpening.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
