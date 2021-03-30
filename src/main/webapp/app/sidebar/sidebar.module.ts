import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from '../shared/shared.module';

import { SIDEBAR_ROUTE } from './';

@NgModule({
    imports: [
      EmobyMphSharedModule,
      RouterModule.forRoot([ SIDEBAR_ROUTE ], { useHash: false })
    ],
    declarations: [],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmobyMphAppSidebarModule {}
