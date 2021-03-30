import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from './projectphase-activity.service';
import { ProjectphaseActivityDeleteDialogComponent } from './projectphase-activity-delete-dialog.component';

@Component({
  selector: 'jhi-projectphase-activity',
  templateUrl: './projectphase-activity.component.html',
})
export class ProjectphaseActivityComponent implements OnInit, OnDestroy {
  projectphaseActivities?: IProjectphaseActivity[];
  eventSubscriber?: Subscription;

  constructor(
    protected projectphaseActivityService: ProjectphaseActivityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.projectphaseActivityService
      .query()
      .subscribe((res: HttpResponse<IProjectphaseActivity[]>) => (this.projectphaseActivities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProjectphaseActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProjectphaseActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProjectphaseActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('projectphaseActivityListModification', () => this.loadAll());
  }

  delete(projectphaseActivity: IProjectphaseActivity): void {
    const modalRef = this.modalService.open(ProjectphaseActivityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.projectphaseActivity = projectphaseActivity;
  }
}
