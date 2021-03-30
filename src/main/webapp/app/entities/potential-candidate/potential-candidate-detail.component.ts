import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPotentialCandidate } from 'app/shared/model/potential-candidate.model';

@Component({
  selector: 'jhi-potential-candidate-detail',
  templateUrl: './potential-candidate-detail.component.html',
})
export class PotentialCandidateDetailComponent implements OnInit {
  potentialCandidate: IPotentialCandidate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ potentialCandidate }) => (this.potentialCandidate = potentialCandidate));
  }

  previousState(): void {
    window.history.back();
  }
}
