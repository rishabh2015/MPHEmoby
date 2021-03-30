import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMobyStatus } from 'app/shared/model/moby-status.model';
import { MobyStatusService } from './moby-status.service';
import { MobyStatusDeleteDialogComponent } from './moby-status-delete-dialog.component';

@Component({
  selector: 'jhi-moby-status',
  templateUrl: './moby-status.component.html',
})
export class MobyStatusComponent implements OnInit, OnDestroy {
  mobyStatuses?: IMobyStatus[];
  eventSubscriber?: Subscription;

  constructor(protected mobyStatusService: MobyStatusService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.mobyStatusService.query().subscribe((res: HttpResponse<IMobyStatus[]>) => (this.mobyStatuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMobyStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMobyStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMobyStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('mobyStatusListModification', () => this.loadAll());
  }

  delete(mobyStatus: IMobyStatus): void {
    const modalRef = this.modalService.open(MobyStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mobyStatus = mobyStatus;
  }
}
