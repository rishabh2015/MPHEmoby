import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CandidateSearchComponent } from './candidate-search.component';

export const CANDIDATE_SEARCH_ROUTE: Route = {
  path: 'candidatesearch',
  component: CandidateSearchComponent,
  data: {
    authorities: ['ROLE_HR', 'ROLE_ADMIN'],
    pageTitle: 'candidatesearch.title',
  },
  canActivate: [UserRouteAccessService],
};
