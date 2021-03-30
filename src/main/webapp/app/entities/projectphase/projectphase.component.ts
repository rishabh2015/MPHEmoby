import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectphase } from 'app/shared/model/projectphase.model';
import { ProjectphaseService } from './projectphase.service';
import { ProjectphaseDeleteDialogComponent } from './projectphase-delete-dialog.component';

@Component({
  selector: 'jhi-projectphase',
  templateUrl: './projectphase.component.html',
})
export class ProjectphaseComponent implements OnInit, OnDestroy {
  projectphases?: IProjectphase[];
  eventSubscriber?: Subscription;

  constructor(
    protected projectphaseService: ProjectphaseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.projectphaseService.query().subscribe((res: HttpResponse<IProjectphase[]>) => (this.projectphases = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProjectphases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProjectphase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProjectphases(): void {
    this.eventSubscriber = this.eventManager.subscribe('projectphaseListModification', () => this.loadAll());
  }

  delete(projectphase: IProjectphase): void {
    const modalRef = this.modalService.open(ProjectphaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.projectphase = projectphase;
  }
}
