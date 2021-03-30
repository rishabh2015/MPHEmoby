import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmobyMphSharedModule } from '../../shared/shared.module';
import { CARD_ROUTE } from './card.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forRoot([CARD_ROUTE], { useHash: false })],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmobyMphAppCardModule {}
