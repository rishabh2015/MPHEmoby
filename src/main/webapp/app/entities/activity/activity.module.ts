import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { ActivityDeleteDialogComponent } from './activity-delete-dialog.component';
import { activityRoute } from './activity.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(activityRoute)],
  declarations: [ActivityComponent, ActivityDetailComponent, ActivityUpdateComponent, ActivityDeleteDialogComponent],
  entryComponents: [ActivityDeleteDialogComponent],
})
export class EmobyMphActivityModule {}
