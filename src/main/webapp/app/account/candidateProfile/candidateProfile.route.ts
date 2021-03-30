import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CandidateProfileComponent } from './candidateProfile.component';
import { Authority } from 'app/shared/constants/authority.constants';

export const candidateProfileRoute: Route = {
  path: 'candidateProfile',
  component: CandidateProfileComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'global.menu.account.settings',
  },
  canActivate: [UserRouteAccessService],
};
