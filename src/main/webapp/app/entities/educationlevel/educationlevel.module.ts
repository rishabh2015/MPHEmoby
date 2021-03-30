import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { EducationlevelComponent } from './educationlevel.component';
import { EducationlevelDetailComponent } from './educationlevel-detail.component';
import { EducationlevelUpdateComponent } from './educationlevel-update.component';
import { EducationlevelDeleteDialogComponent } from './educationlevel-delete-dialog.component';
import { educationlevelRoute } from './educationlevel.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(educationlevelRoute)],
  declarations: [
    EducationlevelComponent,
    EducationlevelDetailComponent,
    EducationlevelUpdateComponent,
    EducationlevelDeleteDialogComponent,
  ],
  entryComponents: [EducationlevelDeleteDialogComponent],
})
export class EmobyMphEducationlevelModule {}
