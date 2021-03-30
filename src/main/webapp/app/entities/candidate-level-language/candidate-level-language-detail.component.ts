import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';

@Component({
  selector: 'jhi-candidate-level-language-detail',
  templateUrl: './candidate-level-language-detail.component.html',
})
export class CandidateLevelLanguageDetailComponent implements OnInit {
  candidateLevelLanguage: ICandidateLevelLanguage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidateLevelLanguage }) => (this.candidateLevelLanguage = candidateLevelLanguage));
  }

  previousState(): void {
    window.history.back();
  }
}
