import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExperience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';
import { ExperienceDeleteDialogComponent } from './experience-delete-dialog.component';

@Component({
  selector: 'jhi-experience',
  templateUrl: './experience.component.html',
})
export class ExperienceComponent implements OnInit, OnDestroy {
  experiences?: IExperience[];
  eventSubscriber?: Subscription;

  constructor(protected experienceService: ExperienceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.experienceService.query().subscribe((res: HttpResponse<IExperience[]>) => (this.experiences = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExperiences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExperience): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExperiences(): void {
    this.eventSubscriber = this.eventManager.subscribe('experienceListModification', () => this.loadAll());
  }

  delete(experience: IExperience): void {
    const modalRef = this.modalService.open(ExperienceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.experience = experience;
  }
}
