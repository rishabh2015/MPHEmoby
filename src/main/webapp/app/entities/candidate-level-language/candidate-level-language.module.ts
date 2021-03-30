import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { CandidateLevelLanguageComponent } from './candidate-level-language.component';
import { CandidateLevelLanguageDetailComponent } from './candidate-level-language-detail.component';
import { CandidateLevelLanguageUpdateComponent } from './candidate-level-language-update.component';
import { CandidateLevelLanguageDeleteDialogComponent } from './candidate-level-language-delete-dialog.component';
import { candidateLevelLanguageRoute } from './candidate-level-language.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(candidateLevelLanguageRoute)],
  declarations: [
    CandidateLevelLanguageComponent,
    CandidateLevelLanguageDetailComponent,
    CandidateLevelLanguageUpdateComponent,
    CandidateLevelLanguageDeleteDialogComponent,
  ],
  entryComponents: [CandidateLevelLanguageDeleteDialogComponent],
})
export class EmobyMphCandidateLevelLanguageModule {}
