import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { IJobdescription, Jobdescription } from 'app/shared/model/jobdescription.model';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { JobdescriptionDetailComponent } from './jobdescription-detail.component';
import { JobdescriptionComponent } from './jobdescription.component';

@Injectable({ providedIn: 'root' })
export class JobdescriptionResolve implements Resolve<IJobdescription> {
  constructor(private service: JobdescriptionService, private router: Router) { }

  resolve(route: ActivatedRouteSnapshot): Observable<IJobdescription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobdescription: HttpResponse<Jobdescription>) => {
          if (jobdescription.body) {
            return of(jobdescription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Jobdescription());
  }
}

export const jobdescriptionRoute: Routes = [
  {
    path: 'jobdescription',
    component: JobdescriptionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'emobyMphApp.jobdescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'jobdescription/:id',
    component: JobdescriptionDetailComponent,
    resolve: {
      jobdescription: JobdescriptionResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'emobyMphApp.jobdescription-detail.home.title',
    },
    canActivate: [UserRouteAccessService],
  }
];
