import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { ProjectphaseActivityComponent } from './projectphase-activity.component';
import { ProjectphaseActivityDetailComponent } from './projectphase-activity-detail.component';
import { ProjectphaseActivityUpdateComponent } from './projectphase-activity-update.component';
import { ProjectphaseActivityDeleteDialogComponent } from './projectphase-activity-delete-dialog.component';
import { projectphaseActivityRoute } from './projectphase-activity.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(projectphaseActivityRoute)],
  declarations: [
    ProjectphaseActivityComponent,
    ProjectphaseActivityDetailComponent,
    ProjectphaseActivityUpdateComponent,
    ProjectphaseActivityDeleteDialogComponent,
  ],
  entryComponents: [ProjectphaseActivityDeleteDialogComponent],
})
export class EmobyMphProjectphaseActivityModule {}
