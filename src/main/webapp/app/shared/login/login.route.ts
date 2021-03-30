import { Route } from '@angular/router';

import { LoginModalComponent } from './login.component';

export const LOGIN_ROUTE: Route = {
  path: '',
  component: LoginModalComponent,
  data: {
    authorities: [],
    pageTitle: 'login.title',
  },
};