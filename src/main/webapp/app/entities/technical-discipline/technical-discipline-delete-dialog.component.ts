import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from './technical-discipline.service';

@Component({
  templateUrl: './technical-discipline-delete-dialog.component.html',
})
export class TechnicalDisciplineDeleteDialogComponent {
  technicalDiscipline?: ITechnicalDiscipline;

  constructor(
    protected technicalDisciplineService: TechnicalDisciplineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.technicalDisciplineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('technicalDisciplineListModification');
      this.activeModal.close();
    });
  }
}
