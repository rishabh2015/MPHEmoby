import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICandidate, Candidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';
import { IEducationlevel } from 'app/shared/model/educationlevel.model';
import { EducationlevelService } from 'app/entities/educationlevel/educationlevel.service';
import { IExperience } from 'app/shared/model/experience.model';
import { ExperienceService } from 'app/entities/experience/experience.service';
import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { SectorsubsectorService } from 'app/entities/sectorsubsector/sectorsubsector.service';
import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from 'app/entities/projectphase-activity/projectphase-activity.service';
import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from 'app/entities/technical-discipline/technical-discipline.service';
import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { IMobyStatus } from 'app/shared/model/moby-status.model';
import { MobyStatusService } from '../moby-status/moby-status.service';

type SelectableEntity =
  | ICountry
  | IEducationlevel
  | IExperience
  | IMobyStatus
  | ISectorsubsector
  | IProjectphaseActivity
  | ITechnicalDiscipline
  | IJobdescription;

type SelectableManyToManyEntity = ICountry | ISectorsubsector | IProjectphaseActivity | ITechnicalDiscipline;

@Component({
  selector: 'jhi-candidate-update',
  templateUrl: './candidate-update.component.html',
})
export class CandidateUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];
  educationlevels: IEducationlevel[] = [];
  experiences: IExperience[] = [];
  mobystatuses: IMobyStatus[] = [];
  sectorsubsectors: ISectorsubsector[] = [];
  projectphaseactivities: IProjectphaseActivity[] = [];
  technicaldisciplines: ITechnicalDiscipline[] = [];
  jobdescriptions: IJobdescription[] = [];
  date_of_birthDp: any;

  editForm = this.fb.group({
    id: [],
    gender: [null, [Validators.required]],
    last_name: [null, [Validators.required]],
    first_name: [null, [Validators.required]],
    email: [],
    phone_number: [],
    date_of_birth: [null, [Validators.required]],
    text_clean: [],
    guid: [null, [Validators.required]],
    creation_date: [],
    update_date: [],
    comment: [],
    nationality: [null, Validators.required],
    dualNationalities: [],
    location: [null, Validators.required],
    educationlevel: [null, Validators.required],
    experience: [null, Validators.required],
    mobyStatus: [],
    sectorSubsectors: [],
    projectphaseActivities: [],
    technicalDisciplines: [],
    shortlistedId: [],
    placedId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected candidateService: CandidateService,
    protected countryService: CountryService,
    protected educationlevelService: EducationlevelService,
    protected experienceService: ExperienceService,
    protected mobyStatusService: MobyStatusService,
    protected sectorSubsectorService: SectorsubsectorService,
    protected projectphaseActivityService: ProjectphaseActivityService,
    protected technicalDisciplineService: TechnicalDisciplineService,
    protected jobdescriptionService: JobdescriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidate }) => {
      if (!candidate.id) {
        const today = moment().startOf('day');
        candidate.creation_date = today;
        candidate.update_date = today;
      }

      this.updateForm(candidate);

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));

      this.educationlevelService.query().subscribe((res: HttpResponse<IEducationlevel[]>) => (this.educationlevels = res.body || []));

      this.experienceService.query().subscribe((res: HttpResponse<IExperience[]>) => (this.experiences = res.body || []));

      this.mobyStatusService.query().subscribe((res: HttpResponse<IMobyStatus[]>) => (this.mobystatuses = res.body || []));

      this.sectorSubsectorService.query().subscribe((res: HttpResponse<ISectorsubsector[]>) => (this.sectorsubsectors = res.body || []));

      this.projectphaseActivityService
        .query()
        .subscribe((res: HttpResponse<IProjectphaseActivity[]>) => (this.projectphaseactivities = res.body || []));

      this.technicalDisciplineService
        .query()
        .subscribe((res: HttpResponse<ITechnicalDiscipline[]>) => (this.technicaldisciplines = res.body || []));

      this.jobdescriptionService.query().subscribe((res: HttpResponse<IJobdescription[]>) => (this.jobdescriptions = res.body || []));
    });
  }

  updateForm(candidate: ICandidate): void {
    this.editForm.patchValue({
      id: candidate.id,
      gender: candidate.gender,
      last_name: candidate.last_name,
      first_name: candidate.first_name,
      email: candidate.email,
      phone_number: candidate.phone_number,
      date_of_birth: candidate.date_of_birth,
      text_clean: candidate.text_clean,
      guid: candidate.guid,
      creation_date: candidate.creation_date ? candidate.creation_date.format(DATE_TIME_FORMAT) : null,
      update_date: candidate.update_date ? candidate.update_date.format(DATE_TIME_FORMAT) : null,
      comment: candidate.comment,
      nationality: candidate.nationality,
      dualNationalities: candidate.dualNationalities,
      location: candidate.location,
      educationlevel: candidate.educationlevel,
      experience: candidate.experience,
      mobyStatus: candidate.mobyStatus,
      sectorSubsectors: candidate.sectorSubsectors,
      projectphaseActivities: candidate.projectphaseActivities,
      technicalDisciplines: candidate.technicalDisciplines,
      shortlistedId: candidate.shortlistedId,
      placedId: candidate.placedId,
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
    const candidate = this.createFromForm();

    if (candidate.id !== undefined) {
      this.subscribeToSaveResponse(this.candidateService.update(candidate));
    } else {
      this.subscribeToSaveResponse(this.candidateService.create(candidate));
    }
  }

  private createFromForm(): ICandidate {
    return {
      ...new Candidate(),
      id: this.editForm.get(['id'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      last_name: this.editForm.get(['last_name'])!.value,
      first_name: this.editForm.get(['first_name'])!.value,
      date_of_birth: this.editForm.get(['date_of_birth'])!.value,
      email: this.editForm.get(['email'])!.value,	
      phone_number: this.editForm.get(['phone_number'])!.value,
      text_clean: this.editForm.get(['text_clean'])!.value,
      guid: this.editForm.get(['guid'])!.value,
      creation_date: this.editForm.get(['creation_date'])!.value
        ? moment(this.editForm.get(['creation_date'])!.value, DATE_TIME_FORMAT)
        : undefined,
      update_date: this.editForm.get(['update_date'])!.value
        ? moment(this.editForm.get(['update_date'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      dualNationalities: this.editForm.get(['dualNationalities'])!.value,
      location: this.editForm.get(['location'])!.value,
      educationlevel: this.editForm.get(['educationlevel'])!.value,
      experience: this.editForm.get(['experience'])!.value,
      mobyStatus: this.editForm.get(['mobyStatus'])!.value,
      sectorSubsectors: this.editForm.get(['sectorSubsectors'])!.value,
      projectphaseActivities: this.editForm.get(['projectphaseActivities'])!.value,
      technicalDisciplines: this.editForm.get(['technicalDisciplines'])!.value,
      shortlistedId: this.editForm.get(['shortlistedId'])!.value,
      placedId: this.editForm.get(['placedId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidate>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

   /* Return true or false if it is the selected */
 compareById(first:any, second:any):boolean {
  return first && second && first.id === second.id;
}
}
