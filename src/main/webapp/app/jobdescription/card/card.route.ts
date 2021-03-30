import { Route } from '@angular/router';
import { CardComponent } from '../card/card.component';

export const CARD_ROUTE: Route = {
  path: 'card',
  component: CardComponent,
  data: {
    authorities: [],
    pageTitle: 'card.title',
  },
};
