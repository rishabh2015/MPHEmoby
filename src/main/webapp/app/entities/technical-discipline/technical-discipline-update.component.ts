import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITechnicalDiscipline, TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from './technical-discipline.service';

@Component({
  selector: 'jhi-technical-discipline-update',
  templateUrl: './technical-discipline-update.component.html',
})
export class TechnicalDisciplineUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(
    protected technicalDisciplineService: TechnicalDisciplineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ technicalDiscipline }) => {
      this.updateForm(technicalDiscipline);
    });
  }

  updateForm(technicalDiscipline: ITechnicalDiscipline): void {
    this.editForm.patchValue({
      id: technicalDiscipline.id,
      code: technicalDiscipline.code,
      libelle: technicalDiscipline.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const technicalDiscipline = this.createFromForm();
    if (technicalDiscipline.id !== undefined) {
      this.subscribeToSaveResponse(this.technicalDisciplineService.update(technicalDiscipline));
    } else {
      this.subscribeToSaveResponse(this.technicalDisciplineService.create(technicalDiscipline));
    }
  }

  private createFromForm(): ITechnicalDiscipline {
    return {
      ...new TechnicalDiscipline(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITechnicalDiscipline>>): void {
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
