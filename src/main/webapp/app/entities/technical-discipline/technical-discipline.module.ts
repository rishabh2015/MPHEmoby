import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { TechnicalDisciplineComponent } from './technical-discipline.component';
import { TechnicalDisciplineDetailComponent } from './technical-discipline-detail.component';
import { TechnicalDisciplineUpdateComponent } from './technical-discipline-update.component';
import { TechnicalDisciplineDeleteDialogComponent } from './technical-discipline-delete-dialog.component';
import { technicalDisciplineRoute } from './technical-discipline.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(technicalDisciplineRoute)],
  declarations: [
    TechnicalDisciplineComponent,
    TechnicalDisciplineDetailComponent,
    TechnicalDisciplineUpdateComponent,
    TechnicalDisciplineDeleteDialogComponent,
  ],
  entryComponents: [TechnicalDisciplineDeleteDialogComponent],
})
export class EmobyMphTechnicalDisciplineModule {}
