import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectphase } from 'app/shared/model/projectphase.model';

@Component({
  selector: 'jhi-projectphase-detail',
  templateUrl: './projectphase-detail.component.html',
})
export class ProjectphaseDetailComponent implements OnInit {
  projectphase: IProjectphase | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectphase }) => (this.projectphase = projectphase));
  }

  previousState(): void {
    window.history.back();
  }
}
