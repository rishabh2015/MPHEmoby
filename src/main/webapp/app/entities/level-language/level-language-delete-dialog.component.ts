import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILevelLanguage } from 'app/shared/model/level-language.model';
import { LevelLanguageService } from './level-language.service';

@Component({
  templateUrl: './level-language-delete-dialog.component.html',
})
export class LevelLanguageDeleteDialogComponent {
  levelLanguage?: ILevelLanguage;

  constructor(
    protected levelLanguageService: LevelLanguageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelLanguageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('levelLanguageListModification');
      this.activeModal.close();
    });
  }
}
