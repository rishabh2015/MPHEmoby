import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILevelLanguage } from 'app/shared/model/level-language.model';
import { LevelLanguageService } from './level-language.service';
import { LevelLanguageDeleteDialogComponent } from './level-language-delete-dialog.component';

@Component({
  selector: 'jhi-level-language',
  templateUrl: './level-language.component.html',
})
export class LevelLanguageComponent implements OnInit, OnDestroy {
  levelLanguages?: ILevelLanguage[];
  eventSubscriber?: Subscription;

  constructor(
    protected levelLanguageService: LevelLanguageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.levelLanguageService.query().subscribe((res: HttpResponse<ILevelLanguage[]>) => (this.levelLanguages = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLevelLanguages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILevelLanguage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLevelLanguages(): void {
    this.eventSubscriber = this.eventManager.subscribe('levelLanguageListModification', () => this.loadAll());
  }

  delete(levelLanguage: ILevelLanguage): void {
    const modalRef = this.modalService.open(LevelLanguageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.levelLanguage = levelLanguage;
  }
}
