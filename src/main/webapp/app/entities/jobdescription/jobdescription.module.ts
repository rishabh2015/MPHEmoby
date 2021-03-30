import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { JobdescriptionComponent } from './jobdescription.component';
import { JobdescriptionDetailComponent } from './jobdescription-detail.component';
import { JobdescriptionUpdateComponent } from './jobdescription-update.component';
import { JobdescriptionDeleteDialogComponent } from './jobdescription-delete-dialog.component';
import { jobdescriptionRoute } from './jobdescription.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(jobdescriptionRoute)],
  declarations: [
    JobdescriptionComponent,
    JobdescriptionDetailComponent,
    JobdescriptionUpdateComponent,
    JobdescriptionDeleteDialogComponent,
  ],
  entryComponents: [JobdescriptionDeleteDialogComponent],
})
export class EmobyMphJobdescriptionModule {}
