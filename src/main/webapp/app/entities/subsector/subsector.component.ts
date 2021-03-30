import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubsector } from 'app/shared/model/subsector.model';
import { SubsectorService } from './subsector.service';
import { SubsectorDeleteDialogComponent } from './subsector-delete-dialog.component';

@Component({
  selector: 'jhi-subsector',
  templateUrl: './subsector.component.html',
})
export class SubsectorComponent implements OnInit, OnDestroy {
  subsectors?: ISubsector[];
  eventSubscriber?: Subscription;

  constructor(protected subsectorService: SubsectorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.subsectorService.query().subscribe((res: HttpResponse<ISubsector[]>) => (this.subsectors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubsectors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubsector): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubsectors(): void {
    this.eventSubscriber = this.eventManager.subscribe('subsectorListModification', () => this.loadAll());
  }

  delete(subsector: ISubsector): void {
    const modalRef = this.modalService.open(SubsectorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subsector = subsector;
  }
}
