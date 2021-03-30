import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobOpening } from 'app/shared/model/job-opening.model';
import { JobOpeningService } from './job-opening.service';
import { JobOpeningDeleteDialogComponent } from './job-opening-delete-dialog.component';

@Component({
  selector: 'jhi-job-opening',
  templateUrl: './job-opening.component.html',
})
export class JobOpeningComponent implements OnInit, OnDestroy {
  jobOpenings?: IJobOpening[];
  eventSubscriber?: Subscription;

  constructor(
    protected jobOpeningService: JobOpeningService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jobOpeningService.query().subscribe((res: HttpResponse<IJobOpening[]>) => (this.jobOpenings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJobOpenings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJobOpening): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInJobOpenings(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobOpeningListModification', () => this.loadAll());
  }

  delete(jobOpening: IJobOpening): void {
    const modalRef = this.modalService.open(JobOpeningDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobOpening = jobOpening;
  }
}
