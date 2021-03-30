import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobdescription } from 'app/shared/model/jobdescription.model';

@Component({
  selector: 'jhi-jobdescription-detail',
  templateUrl: './jobdescription-detail.component.html',
})
export class JobdescriptionDetailComponent implements OnInit {
  jobdescription: IJobdescription | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobdescription }) => (this.jobdescription = jobdescription));
  }

  previousState(): void {
    window.history.back();
  }
}
