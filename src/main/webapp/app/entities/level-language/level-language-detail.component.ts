import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevelLanguage } from 'app/shared/model/level-language.model';

@Component({
  selector: 'jhi-level-language-detail',
  templateUrl: './level-language-detail.component.html',
})
export class LevelLanguageDetailComponent implements OnInit {
  levelLanguage: ILevelLanguage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelLanguage }) => (this.levelLanguage = levelLanguage));
  }

  previousState(): void {
    window.history.back();
  }
}
