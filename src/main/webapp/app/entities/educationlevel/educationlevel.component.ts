import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEducationlevel } from 'app/shared/model/educationlevel.model';
import { EducationlevelService } from './educationlevel.service';
import { EducationlevelDeleteDialogComponent } from './educationlevel-delete-dialog.component';

@Component({
  selector: 'jhi-educationlevel',
  templateUrl: './educationlevel.component.html',
})
export class EducationlevelComponent implements OnInit, OnDestroy {
  educationlevels?: IEducationlevel[];
  eventSubscriber?: Subscription;

  constructor(
    protected educationlevelService: EducationlevelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.educationlevelService.query().subscribe((res: HttpResponse<IEducationlevel[]>) => (this.educationlevels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEducationlevels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEducationlevel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEducationlevels(): void {
    this.eventSubscriber = this.eventManager.subscribe('educationlevelListModification', () => this.loadAll());
  }

  delete(educationlevel: IEducationlevel): void {
    const modalRef = this.modalService.open(EducationlevelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.educationlevel = educationlevel;
  }
}
