import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPotentialCandidate } from 'app/shared/model/potential-candidate.model';
import { PotentialCandidateService } from './potential-candidate.service';

@Component({
  templateUrl: './potential-candidate-delete-dialog.component.html',
})
export class PotentialCandidateDeleteDialogComponent {
  potentialCandidate?: IPotentialCandidate;

  constructor(
    protected potentialCandidateService: PotentialCandidateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.potentialCandidateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('potentialCandidateListModification');
      this.activeModal.close();
    });
  }
}
