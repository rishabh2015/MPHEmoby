import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProjectphaseActivity, ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from './projectphase-activity.service';
import { IProjectphase } from 'app/shared/model/projectphase.model';
import { ProjectphaseService } from 'app/entities/projectphase/projectphase.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';

type SelectableEntity = IProjectphase | IActivity;

@Component({
  selector: 'jhi-projectphase-activity-update',
  templateUrl: './projectphase-activity-update.component.html',
})
export class ProjectphaseActivityUpdateComponent implements OnInit {
  isSaving = false;
  projectphases: IProjectphase[] = [];
  activities: IActivity[] = [];

  editForm = this.fb.group({
    id: [],
    projectphaseId: [null, Validators.required],
    activityId: [null, Validators.required],
  });

  constructor(
    protected projectphaseActivityService: ProjectphaseActivityService,
    protected projectphaseService: ProjectphaseService,
    protected activityService: ActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectphaseActivity }) => {
      this.updateForm(projectphaseActivity);

      this.projectphaseService.query().subscribe((res: HttpResponse<IProjectphase[]>) => (this.projectphases = res.body || []));

      this.activityService.query().subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body || []));
    });
  }

  updateForm(projectphaseActivity: IProjectphaseActivity): void {
    this.editForm.patchValue({
      id: projectphaseActivity.id,
      projectphaseId: projectphaseActivity.projectphaseId,
      activityId: projectphaseActivity.activityId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectphaseActivity = this.createFromForm();
    if (projectphaseActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.projectphaseActivityService.update(projectphaseActivity));
    } else {
      this.subscribeToSaveResponse(this.projectphaseActivityService.create(projectphaseActivity));
    }
  }

  private createFromForm(): IProjectphaseActivity {
    return {
      ...new ProjectphaseActivity(),
      id: this.editForm.get(['id'])!.value,
      projectphaseId: this.editForm.get(['projectphaseId'])!.value,
      activityId: this.editForm.get(['activityId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectphaseActivity>>): void {
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
