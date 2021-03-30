import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { JobOpeningComponent } from './job-opening.component';
import { JobOpeningDetailComponent } from './job-opening-detail.component';
import { JobOpeningUpdateComponent } from './job-opening-update.component';
import { JobOpeningDeleteDialogComponent } from './job-opening-delete-dialog.component';
import { jobOpeningRoute } from './job-opening.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(jobOpeningRoute)],
  declarations: [JobOpeningComponent, JobOpeningDetailComponent, JobOpeningUpdateComponent, JobOpeningDeleteDialogComponent],
  entryComponents: [JobOpeningDeleteDialogComponent],
})
export class EmobyMphJobOpeningModule {}
