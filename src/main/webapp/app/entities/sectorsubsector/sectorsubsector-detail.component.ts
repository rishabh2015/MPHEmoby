import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';

@Component({
  selector: 'jhi-sectorsubsector-detail',
  templateUrl: './sectorsubsector-detail.component.html',
})
export class SectorsubsectorDetailComponent implements OnInit {
  sectorsubsector: ISectorsubsector | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sectorsubsector }) => (this.sectorsubsector = sectorsubsector));
  }

  previousState(): void {
    window.history.back();
  }
}
