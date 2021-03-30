import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICandidateLevelLanguage, CandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';
import { CandidateLevelLanguageService } from './candidate-level-language.service';
import { ILevelLanguage } from 'app/shared/model/level-language.model';
import { LevelLanguageService } from 'app/entities/level-language/level-language.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language/language.service';

type SelectableEntity = ILevelLanguage | ILanguage;

@Component({
  selector: 'jhi-candidate-level-language-update',
  templateUrl: './candidate-level-language-update.component.html',
})
export class CandidateLevelLanguageUpdateComponent implements OnInit {
  isSaving = false;
  levellanguages: ILevelLanguage[] = [];
  languages: ILanguage[] = [];

  editForm = this.fb.group({
    id: [],
    levelLanguageId: [null, Validators.required],
    languageId: [null, Validators.required],
  });

  constructor(
    protected candidateLevelLanguageService: CandidateLevelLanguageService,
    protected levelLanguageService: LevelLanguageService,
    protected languageService: LanguageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidateLevelLanguage }) => {
      this.updateForm(candidateLevelLanguage);

      this.levelLanguageService.query().subscribe((res: HttpResponse<ILevelLanguage[]>) => (this.levellanguages = res.body || []));

      this.languageService.query().subscribe((res: HttpResponse<ILanguage[]>) => (this.languages = res.body || []));
    });
  }

  updateForm(candidateLevelLanguage: ICandidateLevelLanguage): void {
    this.editForm.patchValue({
      id: candidateLevelLanguage.id,
      levelLanguageId: candidateLevelLanguage.levelLanguage,
      languageId: candidateLevelLanguage.language,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidateLevelLanguage = this.createFromForm();
    if (candidateLevelLanguage.id !== undefined) {
      this.subscribeToSaveResponse(this.candidateLevelLanguageService.update(candidateLevelLanguage));
    } else {
      this.subscribeToSaveResponse(this.candidateLevelLanguageService.create(candidateLevelLanguage));
    }
  }

  private createFromForm(): ICandidateLevelLanguage {
    return {
      ...new CandidateLevelLanguage(),
      id: this.editForm.get(['id'])!.value,
      levelLanguage: this.editForm.get(['levelLanguage'])!.value,
      language: this.editForm.get(['language'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidateLevelLanguage>>): void {
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
