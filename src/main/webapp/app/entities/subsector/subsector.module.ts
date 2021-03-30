import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { SubsectorComponent } from './subsector.component';
import { SubsectorDetailComponent } from './subsector-detail.component';
import { SubsectorUpdateComponent } from './subsector-update.component';
import { SubsectorDeleteDialogComponent } from './subsector-delete-dialog.component';
import { subsectorRoute } from './subsector.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(subsectorRoute)],
  declarations: [SubsectorComponent, SubsectorDetailComponent, SubsectorUpdateComponent, SubsectorDeleteDialogComponent],
  entryComponents: [SubsectorDeleteDialogComponent],
})
export class EmobyMphSubsectorModule {}
