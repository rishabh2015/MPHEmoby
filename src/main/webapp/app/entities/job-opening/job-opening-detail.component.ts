import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IJobOpening } from 'app/shared/model/job-opening.model';

@Component({
  selector: 'jhi-job-opening-detail',
  templateUrl: './job-opening-detail.component.html',
})
export class JobOpeningDetailComponent implements OnInit {
  jobOpening: IJobOpening | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobOpening }) => (this.jobOpening = jobOpening));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
