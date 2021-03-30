import { HttpClientModule } from '@angular/common/http';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmobyMphSharedModule } from '../shared/shared.module';
import { CANDIDATE_SEARCH_ROUTE } from './';

@NgModule({
  imports: [EmobyMphSharedModule, HttpClientModule, RouterModule.forRoot([CANDIDATE_SEARCH_ROUTE], { useHash: true })],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EmobyMphAppCandidateSearchModule {}
