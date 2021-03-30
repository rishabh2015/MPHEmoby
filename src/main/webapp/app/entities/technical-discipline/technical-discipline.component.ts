import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from './technical-discipline.service';
import { TechnicalDisciplineDeleteDialogComponent } from './technical-discipline-delete-dialog.component';

@Component({
  selector: 'jhi-technical-discipline',
  templateUrl: './technical-discipline.component.html',
})
export class TechnicalDisciplineComponent implements OnInit, OnDestroy {
  technicalDisciplines?: ITechnicalDiscipline[];
  eventSubscriber?: Subscription;

  constructor(
    protected technicalDisciplineService: TechnicalDisciplineService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.technicalDisciplineService
      .query()
      .subscribe((res: HttpResponse<ITechnicalDiscipline[]>) => (this.technicalDisciplines = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTechnicalDisciplines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITechnicalDiscipline): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTechnicalDisciplines(): void {
    this.eventSubscriber = this.eventManager.subscribe('technicalDisciplineListModification', () => this.loadAll());
  }

  delete(technicalDiscipline: ITechnicalDiscipline): void {
    const modalRef = this.modalService.open(TechnicalDisciplineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.technicalDiscipline = technicalDiscipline;
  }
}
