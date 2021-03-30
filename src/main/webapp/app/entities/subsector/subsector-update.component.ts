import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubsector, Subsector } from 'app/shared/model/subsector.model';
import { SubsectorService } from './subsector.service';

@Component({
  selector: 'jhi-subsector-update',
  templateUrl: './subsector-update.component.html',
})
export class SubsectorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected subsectorService: SubsectorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subsector }) => {
      this.updateForm(subsector);
    });
  }

  updateForm(subsector: ISubsector): void {
    this.editForm.patchValue({
      id: subsector.id,
      code: subsector.code,
      libelle: subsector.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subsector = this.createFromForm();
    if (subsector.id !== undefined) {
      this.subscribeToSaveResponse(this.subsectorService.update(subsector));
    } else {
      this.subscribeToSaveResponse(this.subsectorService.create(subsector));
    }
  }

  private createFromForm(): ISubsector {
    return {
      ...new Subsector(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubsector>>): void {
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
