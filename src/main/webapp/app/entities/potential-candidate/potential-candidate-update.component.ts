import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPotentialCandidate, PotentialCandidate } from 'app/shared/model/potential-candidate.model';
import { PotentialCandidateService } from './potential-candidate.service';
import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate/candidate.service';

type SelectableEntity = IJobdescription | ICandidate;

@Component({
  selector: 'jhi-potential-candidate-update',
  templateUrl: './potential-candidate-update.component.html',
})
export class PotentialCandidateUpdateComponent implements OnInit {
  isSaving = false;
  jobdescriptions: IJobdescription[] = [];
  candidates: ICandidate[] = [];

  editForm = this.fb.group({
    id: [],
    matching_percent: [null, [Validators.required]],
    creation_dt: [null, [Validators.required]],
    jobdescriptionId: [null, Validators.required],
    candidateId: [null, Validators.required],
  });

  constructor(
    protected potentialCandidateService: PotentialCandidateService,
    protected jobdescriptionService: JobdescriptionService,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ potentialCandidate }) => {
      if (!potentialCandidate.id) {
        const today = moment().startOf('day');
        potentialCandidate.creation_dt = today;
      }

      this.updateForm(potentialCandidate);

      this.jobdescriptionService.query().subscribe((res: HttpResponse<IJobdescription[]>) => (this.jobdescriptions = res.body || []));

      this.candidateService.query().subscribe((res: HttpResponse<ICandidate[]>) => (this.candidates = res.body || []));
    });
  }

  updateForm(potentialCandidate: IPotentialCandidate): void {
    this.editForm.patchValue({
      id: potentialCandidate.id,
      matching_percent: potentialCandidate.matching_percent,
      creation_dt: potentialCandidate.creation_dt ? potentialCandidate.creation_dt.format(DATE_TIME_FORMAT) : null,
      jobdescriptionId: potentialCandidate.jobdescriptionId,
      candidateId: potentialCandidate.candidateId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const potentialCandidate = this.createFromForm();
    if (potentialCandidate.id !== undefined) {
      this.subscribeToSaveResponse(this.potentialCandidateService.update(potentialCandidate));
    } else {
      this.subscribeToSaveResponse(this.potentialCandidateService.create(potentialCandidate));
    }
  }

  private createFromForm(): IPotentialCandidate {
    return {
      ...new PotentialCandidate(),
      id: this.editForm.get(['id'])!.value,
      matching_percent: this.editForm.get(['matching_percent'])!.value,
      creation_dt: this.editForm.get(['creation_dt'])!.value
        ? moment(this.editForm.get(['creation_dt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      jobdescriptionId: this.editForm.get(['jobdescriptionId'])!.value,
      candidateId: this.editForm.get(['candidateId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPotentialCandidate>>): void {
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
