import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISectorsubsector, Sectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { SectorsubsectorService } from './sectorsubsector.service';
import { ISector } from 'app/shared/model/sector.model';
import { SectorService } from 'app/entities/sector/sector.service';
import { ISubsector } from 'app/shared/model/subsector.model';
import { SubsectorService } from 'app/entities/subsector/subsector.service';

type SelectableEntity = ISector | ISubsector;

@Component({
  selector: 'jhi-sectorsubsector-update',
  templateUrl: './sectorsubsector-update.component.html',
})
export class SectorsubsectorUpdateComponent implements OnInit {
  isSaving = false;
  sectors: ISector[] = [];
  subsectors: ISubsector[] = [];

  editForm = this.fb.group({
    id: [],
    sectorId: [null, Validators.required],
    subsectorId: [null, Validators.required],
  });

  constructor(
    protected sectorsubsectorService: SectorsubsectorService,
    protected sectorService: SectorService,
    protected subsectorService: SubsectorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sectorsubsector }) => {
      this.updateForm(sectorsubsector);

      this.sectorService.query().subscribe((res: HttpResponse<ISector[]>) => (this.sectors = res.body || []));

      this.subsectorService.query().subscribe((res: HttpResponse<ISubsector[]>) => (this.subsectors = res.body || []));
    });
  }

  updateForm(sectorsubsector: ISectorsubsector): void {
    this.editForm.patchValue({
      id: sectorsubsector.id,
      sectorId: sectorsubsector.sectorId,
      subsectorId: sectorsubsector.subsectorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sectorsubsector = this.createFromForm();
    if (sectorsubsector.id !== undefined) {
      this.subscribeToSaveResponse(this.sectorsubsectorService.update(sectorsubsector));
    } else {
      this.subscribeToSaveResponse(this.sectorsubsectorService.create(sectorsubsector));
    }
  }

  private createFromForm(): ISectorsubsector {
    return {
      ...new Sectorsubsector(),
      id: this.editForm.get(['id'])!.value,
      sectorId: this.editForm.get(['sectorId'])!.value,
      subsectorId: this.editForm.get(['subsectorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISectorsubsector>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
