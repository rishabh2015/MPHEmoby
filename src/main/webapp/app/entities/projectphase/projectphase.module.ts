import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { ProjectphaseComponent } from './projectphase.component';
import { ProjectphaseDetailComponent } from './projectphase-detail.component';
import { ProjectphaseUpdateComponent } from './projectphase-update.component';
import { ProjectphaseDeleteDialogComponent } from './projectphase-delete-dialog.component';
import { projectphaseRoute } from './projectphase.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(projectphaseRoute)],
  declarations: [ProjectphaseComponent, ProjectphaseDetailComponent, ProjectphaseUpdateComponent, ProjectphaseDeleteDialogComponent],
  entryComponents: [ProjectphaseDeleteDialogComponent],
})
export class EmobyMphProjectphaseModule {}
