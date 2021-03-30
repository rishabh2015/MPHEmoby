import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { LevelLanguageComponent } from './level-language.component';
import { LevelLanguageDetailComponent } from './level-language-detail.component';
import { LevelLanguageUpdateComponent } from './level-language-update.component';
import { LevelLanguageDeleteDialogComponent } from './level-language-delete-dialog.component';
import { levelLanguageRoute } from './level-language.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(levelLanguageRoute)],
  declarations: [LevelLanguageComponent, LevelLanguageDetailComponent, LevelLanguageUpdateComponent, LevelLanguageDeleteDialogComponent],
  entryComponents: [LevelLanguageDeleteDialogComponent],
})
export class EmobyMphLevelLanguageModule {}
