import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICandidateLevelLanguage, CandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';
import { CandidateLevelLanguageService } from './candidate-level-language.service';
import { CandidateLevelLanguageComponent } from './candidate-level-language.component';
import { CandidateLevelLanguageDetailComponent } from './candidate-level-language-detail.component';
import { CandidateLevelLanguageUpdateComponent } from './candidate-level-language-update.component';

@Injectable({ providedIn: 'root' })
export class CandidateLevelLanguageResolve implements Resolve<ICandidateLevelLanguage> {
  constructor(private service: CandidateLevelLanguageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICandidateLevelLanguage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((candidateLevelLanguage: HttpResponse<CandidateLevelLanguage>) => {
          if (candidateLevelLanguage.body) {
            return of(candidateLevelLanguage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CandidateLevelLanguage());
  }
}

export const candidateLevelLanguageRoute: Routes = [
  {
    path: '',
    component: CandidateLevelLanguageComponent,
    data: {
      authorities: [Authority.FORBIDDEN],
      pageTitle: 'emobyMphApp.candidateLevelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidateLevelLanguageDetailComponent,
    resolve: {
      candidateLevelLanguage: CandidateLevelLanguageResolve,
    },
    data: {
      authorities: [Authority.FORBIDDEN],
      pageTitle: 'emobyMphApp.candidateLevelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidateLevelLanguageUpdateComponent,
    resolve: {
      candidateLevelLanguage: CandidateLevelLanguageResolve,
    },
    data: {
      authorities: [Authority.FORBIDDEN],
      pageTitle: 'emobyMphApp.candidateLevelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidateLevelLanguageUpdateComponent,
    resolve: {
      candidateLevelLanguage: CandidateLevelLanguageResolve,
    },
    data: {
      authorities: [Authority.FORBIDDEN],
      pageTitle: 'emobyMphApp.candidateLevelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
