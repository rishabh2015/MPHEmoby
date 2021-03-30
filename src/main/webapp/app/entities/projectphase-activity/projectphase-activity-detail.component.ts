import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';

@Component({
  selector: 'jhi-projectphase-activity-detail',
  templateUrl: './projectphase-activity-detail.component.html',
})
export class ProjectphaseActivityDetailComponent implements OnInit {
  projectphaseActivity: IProjectphaseActivity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectphaseActivity }) => (this.projectphaseActivity = projectphaseActivity));
  }

  previousState(): void {
    window.history.back();
  }
}
