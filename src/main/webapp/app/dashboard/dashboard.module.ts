import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmobyMphHomeModule } from 'app/home/home.module';
import { EmobyMphSharedModule } from '../shared/shared.module';
import { DASHBOARD_ROUTE } from './';

@NgModule({
  imports: [EmobyMphSharedModule, EmobyMphHomeModule, RouterModule.forRoot([DASHBOARD_ROUTE], { useHash: false })],
  declarations: [],

  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmobyMphAppDashboardModule {}
