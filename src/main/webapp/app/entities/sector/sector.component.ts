import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISector } from 'app/shared/model/sector.model';
import { SectorService } from './sector.service';
import { SectorDeleteDialogComponent } from './sector-delete-dialog.component';

@Component({
  selector: 'jhi-sector',
  templateUrl: './sector.component.html',
})
export class SectorComponent implements OnInit, OnDestroy {
  sectors?: ISector[];
  eventSubscriber?: Subscription;

  constructor(protected sectorService: SectorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.sectorService.query().subscribe((res: HttpResponse<ISector[]>) => (this.sectors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSectors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISector): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSectors(): void {
    this.eventSubscriber = this.eventManager.subscribe('sectorListModification', () => this.loadAll());
  }

  delete(sector: ISector): void {
    const modalRef = this.modalService.open(SectorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sector = sector;
  }
}
