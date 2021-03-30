import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISectorsubsector, Sectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { SectorsubsectorService } from './sectorsubsector.service';
import { SectorsubsectorComponent } from './sectorsubsector.component';
import { SectorsubsectorDetailComponent } from './sectorsubsector-detail.component';
import { SectorsubsectorUpdateComponent } from './sectorsubsector-update.component';

@Injectable({ providedIn: 'root' })
export class SectorsubsectorResolve implements Resolve<ISectorsubsector> {
  constructor(private service: SectorsubsectorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISectorsubsector> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sectorsubsector: HttpResponse<Sectorsubsector>) => {
          if (sectorsubsector.body) {
            return of(sectorsubsector.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sectorsubsector());
  }
}

export const sectorsubsectorRoute: Routes = [
  {
    path: '',
    component: SectorsubsectorComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sectorsubsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SectorsubsectorDetailComponent,
    resolve: {
      sectorsubsector: SectorsubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sectorsubsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SectorsubsectorUpdateComponent,
    resolve: {
      sectorsubsector: SectorsubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sectorsubsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SectorsubsectorUpdateComponent,
    resolve: {
      sectorsubsector: SectorsubsectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sectorsubsector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
