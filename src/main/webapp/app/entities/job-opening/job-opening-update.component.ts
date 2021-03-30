import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IJobOpening, JobOpening } from 'app/shared/model/job-opening.model';
import { JobOpeningService } from './job-opening.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-job-opening-update',
  templateUrl: './job-opening-update.component.html',
})
export class JobOpeningUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    guid: [],
    text_clean: [],
    file: [null, [Validators.required]],
    fileContentType: [],
    creation_date: [],
    delete_date: [],
    jobdescription_text: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jobOpeningService: JobOpeningService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobOpening }) => {
      if (!jobOpening.id) {
        const today = moment().startOf('day');
        jobOpening.creation_date = today;
        jobOpening.delete_date = today;
      }

      this.updateForm(jobOpening);
    });
  }

  updateForm(jobOpening: IJobOpening): void {
    this.editForm.patchValue({
      id: jobOpening.id,
      title: jobOpening.title,
      guid: jobOpening.guid,
      text_clean: jobOpening.text_clean,
      file: jobOpening.file,
      fileContentType: jobOpening.fileContentType,
      creation_date: jobOpening.creation_date ? jobOpening.creation_date.format(DATE_TIME_FORMAT) : null,
      delete_date: jobOpening.delete_date ? jobOpening.delete_date.format(DATE_TIME_FORMAT) : null,
      jobdescription_text: jobOpening.jobdescription_text,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('emobyMphApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobOpening = this.createFromForm();
    if (jobOpening.id !== undefined) {
      this.subscribeToSaveResponse(this.jobOpeningService.update(jobOpening));
    } else {
      this.subscribeToSaveResponse(this.jobOpeningService.create(jobOpening));
    }
  }

  private createFromForm(): IJobOpening {
    return {
      ...new JobOpening(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      guid: this.editForm.get(['guid'])!.value,
      text_clean: this.editForm.get(['text_clean'])!.value,
      fileContentType: this.editForm.get(['fileContentType'])!.value,
      file: this.editForm.get(['file'])!.value,
      creation_date: this.editForm.get(['creation_date'])!.value
        ? moment(this.editForm.get(['creation_date'])!.value, DATE_TIME_FORMAT)
        : undefined,
      delete_date: this.editForm.get(['delete_date'])!.value
        ? moment(this.editForm.get(['delete_date'])!.value, DATE_TIME_FORMAT)
        : undefined,
      jobdescription_text: this.editForm.get(['jobdescription_text'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobOpening>>): void {
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
