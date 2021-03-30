import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMobyStatus, MobyStatus } from 'app/shared/model/moby-status.model';
import { MobyStatusService } from './moby-status.service';

@Component({
  selector: 'jhi-moby-status-update',
  templateUrl: './moby-status-update.component.html',
})
export class MobyStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected mobyStatusService: MobyStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mobyStatus }) => {
      this.updateForm(mobyStatus);
    });
  }

  updateForm(mobyStatus: IMobyStatus): void {
    this.editForm.patchValue({
      id: mobyStatus.id,
      code: mobyStatus.code,
      libelle: mobyStatus.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mobyStatus = this.createFromForm();
    if (mobyStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.mobyStatusService.update(mobyStatus));
    } else {
      this.subscribeToSaveResponse(this.mobyStatusService.create(mobyStatus));
    }
  }

  private createFromForm(): IMobyStatus {
    return {
      ...new MobyStatus(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMobyStatus>>): void {
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
