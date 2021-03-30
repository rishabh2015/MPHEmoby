import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { SectorsubsectorService } from './sectorsubsector.service';

@Component({
  templateUrl: './sectorsubsector-delete-dialog.component.html',
})
export class SectorsubsectorDeleteDialogComponent {
  sectorsubsector?: ISectorsubsector;

  constructor(
    protected sectorsubsectorService: SectorsubsectorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sectorsubsectorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sectorsubsectorListModification');
      this.activeModal.close();
    });
  }
}
