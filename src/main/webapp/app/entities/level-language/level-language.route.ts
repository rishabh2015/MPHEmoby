import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILevelLanguage, LevelLanguage } from 'app/shared/model/level-language.model';
import { LevelLanguageService } from './level-language.service';
import { LevelLanguageComponent } from './level-language.component';
import { LevelLanguageDetailComponent } from './level-language-detail.component';
import { LevelLanguageUpdateComponent } from './level-language-update.component';

@Injectable({ providedIn: 'root' })
export class LevelLanguageResolve implements Resolve<ILevelLanguage> {
  constructor(private service: LevelLanguageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILevelLanguage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((levelLanguage: HttpResponse<LevelLanguage>) => {
          if (levelLanguage.body) {
            return of(levelLanguage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LevelLanguage());
  }
}

export const levelLanguageRoute: Routes = [
  {
    path: '',
    component: LevelLanguageComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.levelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelLanguageDetailComponent,
    resolve: {
      levelLanguage: LevelLanguageResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.levelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelLanguageUpdateComponent,
    resolve: {
      levelLanguage: LevelLanguageResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.levelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelLanguageUpdateComponent,
    resolve: {
      levelLanguage: LevelLanguageResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.levelLanguage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
