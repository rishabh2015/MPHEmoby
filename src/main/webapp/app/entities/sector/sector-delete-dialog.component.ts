import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISector } from 'app/shared/model/sector.model';
import { SectorService } from './sector.service';

@Component({
  templateUrl: './sector-delete-dialog.component.html',
})
export class SectorDeleteDialogComponent {
  sector?: ISector;

  constructor(protected sectorService: SectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sectorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sectorListModification');
      this.activeModal.close();
    });
  }
}
