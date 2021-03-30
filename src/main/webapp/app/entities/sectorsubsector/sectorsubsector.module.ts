import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { SectorsubsectorComponent } from './sectorsubsector.component';
import { SectorsubsectorDetailComponent } from './sectorsubsector-detail.component';
import { SectorsubsectorUpdateComponent } from './sectorsubsector-update.component';
import { SectorsubsectorDeleteDialogComponent } from './sectorsubsector-delete-dialog.component';
import { sectorsubsectorRoute } from './sectorsubsector.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(sectorsubsectorRoute)],
  declarations: [
    SectorsubsectorComponent,
    SectorsubsectorDetailComponent,
    SectorsubsectorUpdateComponent,
    SectorsubsectorDeleteDialogComponent,
  ],
  entryComponents: [SectorsubsectorDeleteDialogComponent],
})
export class EmobyMphSectorsubsectorModule {}
