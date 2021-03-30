import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISector, Sector } from 'app/shared/model/sector.model';
import { SectorService } from './sector.service';

@Component({
  selector: 'jhi-sector-update',
  templateUrl: './sector-update.component.html',
})
export class SectorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected sectorService: SectorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sector }) => {
      this.updateForm(sector);
    });
  }

  updateForm(sector: ISector): void {
    this.editForm.patchValue({
      id: sector.id,
      code: sector.code,
      libelle: sector.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sector = this.createFromForm();
    if (sector.id !== undefined) {
      this.subscribeToSaveResponse(this.sectorService.update(sector));
    } else {
      this.subscribeToSaveResponse(this.sectorService.create(sector));
    }
  }

  private createFromForm(): ISector {
    return {
      ...new Sector(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISector>>): void {
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
}
