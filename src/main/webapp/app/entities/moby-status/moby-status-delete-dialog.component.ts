import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMobyStatus } from 'app/shared/model/moby-status.model';
import { MobyStatusService } from './moby-status.service';

@Component({
  templateUrl: './moby-status-delete-dialog.component.html',
})
export class MobyStatusDeleteDialogComponent {
  mobyStatus?: IMobyStatus;

  constructor(
    protected mobyStatusService: MobyStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mobyStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mobyStatusListModification');
      this.activeModal.close();
    });
  }
}
