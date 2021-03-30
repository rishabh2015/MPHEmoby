import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SidebarComponent } from './sidebar.component';

export const SIDEBAR_ROUTE: Route = {
  path: 'sidebar',
  component: SidebarComponent,
  data: {
    authorities: [],
    pageTitle: 'sidebar.title'
  },
  canActivate: [UserRouteAccessService]
};
