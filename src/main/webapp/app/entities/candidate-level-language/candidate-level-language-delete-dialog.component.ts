import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';
import { CandidateLevelLanguageService } from './candidate-level-language.service';

@Component({
  templateUrl: './candidate-level-language-delete-dialog.component.html',
})
export class CandidateLevelLanguageDeleteDialogComponent {
  candidateLevelLanguage?: ICandidateLevelLanguage;

  constructor(
    protected candidateLevelLanguageService: CandidateLevelLanguageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidateLevelLanguageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('candidateLevelLanguageListModification');
      this.activeModal.close();
    });
  }
}
