import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobOpening } from 'app/shared/model/job-opening.model';
import { JobOpeningService } from './job-opening.service';

@Component({
  templateUrl: './job-opening-delete-dialog.component.html',
})
export class JobOpeningDeleteDialogComponent {
  jobOpening?: IJobOpening;

  constructor(
    protected jobOpeningService: JobOpeningService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobOpeningService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobOpeningListModification');
      this.activeModal.close();
    });
  }
}
