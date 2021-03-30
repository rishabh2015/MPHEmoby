import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';

@Component({
  selector: 'jhi-technical-discipline-detail',
  templateUrl: './technical-discipline-detail.component.html',
})
export class TechnicalDisciplineDetailComponent implements OnInit {
  technicalDiscipline: ITechnicalDiscipline | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ technicalDiscipline }) => (this.technicalDiscipline = technicalDiscipline));
  }

  previousState(): void {
    window.history.back();
  }
}
