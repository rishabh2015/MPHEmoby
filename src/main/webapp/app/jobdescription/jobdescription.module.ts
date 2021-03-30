import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmobyMphSharedModule } from '../shared/shared.module';
import { jobdescriptionRoute } from './jobdescription.route';
import { EmobyMphHomeModule } from 'app/home/home.module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PaginatorModule } from 'primeng/paginator';

@NgModule({
  imports: [
    EmobyMphSharedModule,
    EmobyMphHomeModule,
    BrowserModule,
    BrowserAnimationsModule,
    PaginatorModule,

    RouterModule.forChild(jobdescriptionRoute),
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmobyMphAppJobdescriptionModule {}
