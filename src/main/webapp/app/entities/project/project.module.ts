import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { ProjectComponent } from './project.component';
import { ProjectDetailComponent } from './project-detail.component';
import { ProjectUpdateComponent } from './project-update.component';
import { ProjectDeleteDialogComponent } from './project-delete-dialog.component';
import { projectRoute } from './project.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(projectRoute)],
  declarations: [ProjectComponent, ProjectDetailComponent, ProjectUpdateComponent, ProjectDeleteDialogComponent],
  entryComponents: [ProjectDeleteDialogComponent],
})
export class EmobyMphProjectModule {}
