import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { SectorsubsectorService } from './sectorsubsector.service';
import { SectorsubsectorDeleteDialogComponent } from './sectorsubsector-delete-dialog.component';

@Component({
  selector: 'jhi-sectorsubsector',
  templateUrl: './sectorsubsector.component.html',
})
export class SectorsubsectorComponent implements OnInit, OnDestroy {
  sectorsubsectors?: ISectorsubsector[];
  eventSubscriber?: Subscription;

  constructor(
    protected sectorsubsectorService: SectorsubsectorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.sectorsubsectorService.query().subscribe((res: HttpResponse<ISectorsubsector[]>) => (this.sectorsubsectors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSectorsubsectors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISectorsubsector): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSectorsubsectors(): void {
    this.eventSubscriber = this.eventManager.subscribe('sectorsubsectorListModification', () => this.loadAll());
  }

  delete(sectorsubsector: ISectorsubsector): void {
    const modalRef = this.modalService.open(SectorsubsectorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sectorsubsector = sectorsubsector;
  }
}
