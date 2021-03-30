import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectphase } from 'app/shared/model/projectphase.model';
import { ProjectphaseService } from './projectphase.service';

@Component({
  templateUrl: './projectphase-delete-dialog.component.html',
})
export class ProjectphaseDeleteDialogComponent {
  projectphase?: IProjectphase;

  constructor(
    protected projectphaseService: ProjectphaseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectphaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectphaseListModification');
      this.activeModal.close();
    });
  }
}
