import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEducationlevel, Educationlevel } from 'app/shared/model/educationlevel.model';
import { EducationlevelService } from './educationlevel.service';

@Component({
  selector: 'jhi-educationlevel-update',
  templateUrl: './educationlevel-update.component.html',
})
export class EducationlevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected educationlevelService: EducationlevelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationlevel }) => {
      this.updateForm(educationlevel);
    });
  }

  updateForm(educationlevel: IEducationlevel): void {
    this.editForm.patchValue({
      id: educationlevel.id,
      code: educationlevel.code,
      libelle: educationlevel.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const educationlevel = this.createFromForm();
    if (educationlevel.id !== undefined) {
      this.subscribeToSaveResponse(this.educationlevelService.update(educationlevel));
    } else {
      this.subscribeToSaveResponse(this.educationlevelService.create(educationlevel));
    }
  }

  private createFromForm(): IEducationlevel {
    return {
      ...new Educationlevel(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducationlevel>>): void {
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
