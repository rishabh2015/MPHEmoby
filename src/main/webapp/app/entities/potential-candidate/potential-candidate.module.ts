import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { PotentialCandidateComponent } from './potential-candidate.component';
import { PotentialCandidateDetailComponent } from './potential-candidate-detail.component';
import { PotentialCandidateUpdateComponent } from './potential-candidate-update.component';
import { PotentialCandidateDeleteDialogComponent } from './potential-candidate-delete-dialog.component';
import { potentialCandidateRoute } from './potential-candidate.route';

@NgModule({
  imports: [EmobyMphSharedModule, RouterModule.forChild(potentialCandidateRoute)],
  declarations: [
    PotentialCandidateComponent,
    PotentialCandidateDetailComponent,
    PotentialCandidateUpdateComponent,
    PotentialCandidateDeleteDialogComponent,
  ],
  entryComponents: [PotentialCandidateDeleteDialogComponent],
})
export class EmobyMphPotentialCandidateModule {}
