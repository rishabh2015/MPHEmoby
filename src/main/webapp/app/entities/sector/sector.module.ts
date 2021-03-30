import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { SectorComponent } from './sector.component';
import { SectorDetailComponent } from './sector-detail.component';
import { SectorUpdateComponent } from './sector-update.component';
import { SectorDeleteDialogComponent } from './sector-delete-dialog.component';
import { sectorRoute } from './sector.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(sectorRoute)],
  declarations: [SectorComponent, SectorDetailComponent, SectorUpdateComponent, SectorDeleteDialogComponent],
  entryComponents: [SectorDeleteDialogComponent],
})
export class EmobyMphSectorModule {}
