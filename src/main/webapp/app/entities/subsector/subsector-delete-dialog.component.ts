import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubsector } from 'app/shared/model/subsector.model';
import { SubsectorService } from './subsector.service';

@Component({
  templateUrl: './subsector-delete-dialog.component.html',
})
export class SubsectorDeleteDialogComponent {
  subsector?: ISubsector;

  constructor(protected subsectorService: SubsectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subsectorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subsectorListModification');
      this.activeModal.close();
    });
  }
}
