import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { MobyStatusComponent } from './moby-status.component';
import { MobyStatusDetailComponent } from './moby-status-detail.component';
import { MobyStatusUpdateComponent } from './moby-status-update.component';
import { MobyStatusDeleteDialogComponent } from './moby-status-delete-dialog.component';
import { mobyStatusRoute } from './moby-status.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(mobyStatusRoute)],
  declarations: [MobyStatusComponent, MobyStatusDetailComponent, MobyStatusUpdateComponent, MobyStatusDeleteDialogComponent],
  entryComponents: [MobyStatusDeleteDialogComponent],
})
export class EmobyMphMobyStatusModule {}
