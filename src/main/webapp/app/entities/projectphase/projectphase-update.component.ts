import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProjectphase, Projectphase } from 'app/shared/model/projectphase.model';
import { ProjectphaseService } from './projectphase.service';

@Component({
  selector: 'jhi-projectphase-update',
  templateUrl: './projectphase-update.component.html',
})
export class ProjectphaseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(protected projectphaseService: ProjectphaseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectphase }) => {
      this.updateForm(projectphase);
    });
  }

  updateForm(projectphase: IProjectphase): void {
    this.editForm.patchValue({
      id: projectphase.id,
      code: projectphase.code,
      libelle: projectphase.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectphase = this.createFromForm();
    if (projectphase.id !== undefined) {
      this.subscribeToSaveResponse(this.projectphaseService.update(projectphase));
    } else {
      this.subscribeToSaveResponse(this.projectphaseService.create(projectphase));
    }
  }

  private createFromForm(): IProjectphase {
    return {
      ...new Projectphase(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectphase>>): void {
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
