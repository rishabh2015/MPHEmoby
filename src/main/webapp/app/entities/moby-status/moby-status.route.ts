import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMobyStatus, MobyStatus } from 'app/shared/model/moby-status.model';
import { MobyStatusService } from './moby-status.service';
import { MobyStatusComponent } from './moby-status.component';
import { MobyStatusDetailComponent } from './moby-status-detail.component';
import { MobyStatusUpdateComponent } from './moby-status-update.component';

@Injectable({ providedIn: 'root' })
export class MobyStatusResolve implements Resolve<IMobyStatus> {
  constructor(private service: MobyStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMobyStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mobyStatus: HttpResponse<MobyStatus>) => {
          if (mobyStatus.body) {
            return of(mobyStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MobyStatus());
  }
}

export const mobyStatusRoute: Routes = [
  {
    path: '',
    component: MobyStatusComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.mobyStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MobyStatusDetailComponent,
    resolve: {
      mobyStatus: MobyStatusResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.mobyStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MobyStatusUpdateComponent,
    resolve: {
      mobyStatus: MobyStatusResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.mobyStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MobyStatusUpdateComponent,
    resolve: {
      mobyStatus: MobyStatusResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.mobyStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
