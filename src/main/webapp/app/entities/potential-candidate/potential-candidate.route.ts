import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPotentialCandidate, PotentialCandidate } from 'app/shared/model/potential-candidate.model';
import { PotentialCandidateService } from './potential-candidate.service';
import { PotentialCandidateComponent } from './potential-candidate.component';
import { PotentialCandidateDetailComponent } from './potential-candidate-detail.component';
import { PotentialCandidateUpdateComponent } from './potential-candidate-update.component';

@Injectable({ providedIn: 'root' })
export class PotentialCandidateResolve implements Resolve<IPotentialCandidate> {
  constructor(private service: PotentialCandidateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPotentialCandidate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((potentialCandidate: HttpResponse<PotentialCandidate>) => {
          if (potentialCandidate.body) {
            return of(potentialCandidate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PotentialCandidate());
  }
}

export const potentialCandidateRoute: Routes = [
  {
    path: '',
    component: PotentialCandidateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.potentialCandidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PotentialCandidateDetailComponent,
    resolve: {
      potentialCandidate: PotentialCandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.potentialCandidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PotentialCandidateUpdateComponent,
    resolve: {
      potentialCandidate: PotentialCandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.potentialCandidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PotentialCandidateUpdateComponent,
    resolve: {
      potentialCandidate: PotentialCandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'emobyMphApp.potentialCandidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
