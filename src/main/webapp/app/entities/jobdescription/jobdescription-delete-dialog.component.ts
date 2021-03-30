import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { JobdescriptionService } from './jobdescription.service';

@Component({
  templateUrl: './jobdescription-delete-dialog.component.html',
})
export class JobdescriptionDeleteDialogComponent {
  jobdescription?: IJobdescription;

  constructor(
    protected jobdescriptionService: JobdescriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobdescriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobdescriptionListModification');
      this.activeModal.close();
    });
  }
}
