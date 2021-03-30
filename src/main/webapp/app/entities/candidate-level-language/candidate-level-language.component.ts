import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';
import { CandidateLevelLanguageService } from './candidate-level-language.service';
import { CandidateLevelLanguageDeleteDialogComponent } from './candidate-level-language-delete-dialog.component';

@Component({
  selector: 'jhi-candidate-level-language',
  templateUrl: './candidate-level-language.component.html',
})
export class CandidateLevelLanguageComponent implements OnInit, OnDestroy {
  candidateLevelLanguages?: ICandidateLevelLanguage[];
  eventSubscriber?: Subscription;

  constructor(
    protected candidateLevelLanguageService: CandidateLevelLanguageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.candidateLevelLanguageService
      .query()
      .subscribe((res: HttpResponse<ICandidateLevelLanguage[]>) => (this.candidateLevelLanguages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCandidateLevelLanguages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICandidateLevelLanguage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCandidateLevelLanguages(): void {
    this.eventSubscriber = this.eventManager.subscribe('candidateLevelLanguageListModification', () => this.loadAll());
  }

  delete(candidateLevelLanguage: ICandidateLevelLanguage): void {
    const modalRef = this.modalService.open(CandidateLevelLanguageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidateLevelLanguage = candidateLevelLanguage;
  }
}
