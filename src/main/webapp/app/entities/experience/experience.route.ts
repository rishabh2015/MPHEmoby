import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExperience, Experience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';
import { ExperienceComponent } from './experience.component';
import { ExperienceDetailComponent } from './experience-detail.component';
import { ExperienceUpdateComponent } from './experience-update.component';

@Injectable({ providedIn: 'root' })
export class ExperienceResolve implements Resolve<IExperience> {
  constructor(private service: ExperienceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExperience> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((experience: HttpResponse<Experience>) => {
          if (experience.body) {
            return of(experience.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Experience());
  }
}

export const experienceRoute: Routes = [
  {
    path: '',
    component: ExperienceComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.experience.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExperienceDetailComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.experience.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.experience.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExperienceUpdateComponent,
    resolve: {
      experience: ExperienceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.experience.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
