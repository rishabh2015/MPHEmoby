import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMobyStatus } from 'app/shared/model/moby-status.model';

@Component({
  selector: 'jhi-moby-status-detail',
  templateUrl: './moby-status-detail.component.html',
})
export class MobyStatusDetailComponent implements OnInit {
  mobyStatus: IMobyStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mobyStatus }) => (this.mobyStatus = mobyStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
