import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEducationlevel } from 'app/shared/model/educationlevel.model';
import { EducationlevelService } from './educationlevel.service';

@Component({
  templateUrl: './educationlevel-delete-dialog.component.html',
})
export class EducationlevelDeleteDialogComponent {
  educationlevel?: IEducationlevel;

  constructor(
    protected educationlevelService: EducationlevelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationlevelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('educationlevelListModification');
      this.activeModal.close();
    });
  }
}
