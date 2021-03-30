import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from '../shared.module';

import { LOGIN_ROUTE } from './login.route';

@NgModule({
    imports: [
      EmobyMphSharedModule,
      RouterModule.forRoot([ LOGIN_ROUTE ], { useHash: false })
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmobyMphAppLoginModule {}
