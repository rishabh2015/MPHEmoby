import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from './projectphase-activity.service';

@Component({
  templateUrl: './projectphase-activity-delete-dialog.component.html',
})
export class ProjectphaseActivityDeleteDialogComponent {
  projectphaseActivity?: IProjectphaseActivity;

  constructor(
    protected projectphaseActivityService: ProjectphaseActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectphaseActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectphaseActivityListModification');
      this.activeModal.close();
    });
  }
}
