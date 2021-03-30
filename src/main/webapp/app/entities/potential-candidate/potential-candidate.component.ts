import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPotentialCandidate } from 'app/shared/model/potential-candidate.model';
import { PotentialCandidateService } from './potential-candidate.service';
import { PotentialCandidateDeleteDialogComponent } from './potential-candidate-delete-dialog.component';

@Component({
  selector: 'jhi-potential-candidate',
  templateUrl: './potential-candidate.component.html',
})
export class PotentialCandidateComponent implements OnInit, OnDestroy {
  potentialCandidates?: IPotentialCandidate[];
  eventSubscriber?: Subscription;

  constructor(
    protected potentialCandidateService: PotentialCandidateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.potentialCandidateService
      .query()
      .subscribe((res: HttpResponse<IPotentialCandidate[]>) => (this.potentialCandidates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPotentialCandidates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPotentialCandidate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPotentialCandidates(): void {
    this.eventSubscriber = this.eventManager.subscribe('potentialCandidateListModification', () => this.loadAll());
  }

  delete(potentialCandidate: IPotentialCandidate): void {
    const modalRef = this.modalService.open(PotentialCandidateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.potentialCandidate = potentialCandidate;
  }
}
