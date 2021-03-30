import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISector, Sector } from 'app/shared/model/sector.model';
import { SectorService } from './sector.service';
import { SectorComponent } from './sector.component';
import { SectorDetailComponent } from './sector-detail.component';
import { SectorUpdateComponent } from './sector-update.component';

@Injectable({ providedIn: 'root' })
export class SectorResolve implements Resolve<ISector> {
  constructor(private service: SectorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISector> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sector: HttpResponse<Sector>) => {
          if (sector.body) {
            return of(sector.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sector());
  }
}

export const sectorRoute: Routes = [
  {
    path: '',
    component: SectorComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SectorDetailComponent,
    resolve: {
      sector: SectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SectorUpdateComponent,
    resolve: {
      sector: SectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SectorUpdateComponent,
    resolve: {
      sector: SectorResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'emobyMphApp.sector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
