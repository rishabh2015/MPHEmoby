import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILevelLanguage, LevelLanguage } from 'app/shared/model/level-language.model';
import { LevelLanguageService } from './level-language.service';

@Component({
  selector: 'jhi-level-language-update',
  templateUrl: './level-language-update.component.html',
})
export class LevelLanguageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected levelLanguageService: LevelLanguageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelLanguage }) => {
      this.updateForm(levelLanguage);
    });
  }

  updateForm(levelLanguage: ILevelLanguage): void {
    this.editForm.patchValue({
      id: levelLanguage.id,
      code: levelLanguage.code,
      libelle: levelLanguage.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const levelLanguage = this.createFromForm();
    if (levelLanguage.id !== undefined) {
      this.subscribeToSaveResponse(this.levelLanguageService.update(levelLanguage));
    } else {
      this.subscribeToSaveResponse(this.levelLanguageService.create(levelLanguage));
    }
  }

  private createFromForm(): ILevelLanguage {
    return {
      ...new LevelLanguage(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevelLanguage>>): void {
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
