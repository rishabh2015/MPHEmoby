import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJobdescription, Jobdescription } from 'app/shared/model/jobdescription.model';
import { JobdescriptionService } from './jobdescription.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';
import { IEducationlevel } from 'app/shared/model/educationlevel.model';
import { EducationlevelService } from 'app/entities/educationlevel/educationlevel.service';
import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ProjectphaseActivityService } from 'app/entities/projectphase-activity/projectphase-activity.service';
import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { TechnicalDisciplineService } from 'app/entities/technical-discipline/technical-discipline.service';
import { IExperience } from 'app/shared/model/experience.model';
import { ExperienceService } from 'app/entities/experience/experience.service';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language/language.service';
import { ISector } from 'app/shared/model/sector.model';
import { SectorService } from 'app/entities/sector/sector.service';
import { Content, IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content/content.service';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { AlertError } from 'app/shared/alert/alert-error.model';

type SelectableEntity =
  | IProject
  | ICountry
  | IEducationlevel
  | IProjectphaseActivity
  | ITechnicalDiscipline
  | IExperience
  | ILanguage
  | ISector
  | IContent;

type SelectableManyToManyEntity =
  | ICountry
  | IEducationlevel
  | IProjectphaseActivity
  | ITechnicalDiscipline
  | IExperience
  | ILanguage
  | ISector;

@Component({
  selector: 'jhi-jobdescription-update',
  templateUrl: './jobdescription-update.component.html',
})
export class JobdescriptionUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProject[] = [];
  countries: ICountry[] = [];
  educationlevels: IEducationlevel[] = [];
  projectphaseactivities: IProjectphaseActivity[] = [];
  technicaldisciplines: ITechnicalDiscipline[] = [];
  experiences: IExperience[] = [];
  languages: ILanguage[] = [];
  sectors: ISector[] = [];
  content?: IContent;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    creation_dt: [null, [Validators.required]],
    gender: [],
    filename: [null, [Validators.required]],
    projectId: [null, Validators.required],
    nationalities: [],
    locations: [],
    educationlevels: [],
    projectphaseActivities: [],
    technicalDisciplines: [],
    experiences: [],
    languages: [],
    sectors: [],
    contentId: [],
    dataContentType: [],
    data: [],
    text: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jobdescriptionService: JobdescriptionService,
    protected projectService: ProjectService,
    protected countryService: CountryService,
    protected educationlevelService: EducationlevelService,
    protected projectphaseActivityService: ProjectphaseActivityService,
    protected technicalDisciplineService: TechnicalDisciplineService,
    protected experienceService: ExperienceService,
    protected languageService: LanguageService,
    protected sectorService: SectorService,
    protected contentService: ContentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobdescription }) => {
      if (!jobdescription.id) {
        const today = moment().startOf('day');
        jobdescription.creation_dt = today;
      }
      this.updateForm(jobdescription);

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));

      this.educationlevelService.query().subscribe((res: HttpResponse<IEducationlevel[]>) => (this.educationlevels = res.body || []));

      this.projectphaseActivityService
        .query()
        .subscribe((res: HttpResponse<IProjectphaseActivity[]>) => (this.projectphaseactivities = res.body || []));

      this.technicalDisciplineService
        .query()
        .subscribe((res: HttpResponse<ITechnicalDiscipline[]>) => (this.technicaldisciplines = res.body || []));

      this.experienceService.query().subscribe((res: HttpResponse<IExperience[]>) => (this.experiences = res.body || []));

      this.languageService.query().subscribe((res: HttpResponse<ILanguage[]>) => (this.languages = res.body || []));

      this.sectorService.query().subscribe((res: HttpResponse<ISector[]>) => (this.sectors = res.body || []));

    });
  }

  updateForm(jobdescription: IJobdescription): void {
    this.editForm.patchValue({
      id: jobdescription.id,
      name: jobdescription.name,
      creation_dt: jobdescription.creation_dt ? jobdescription.creation_dt.format(DATE_TIME_FORMAT) : null,
      gender: jobdescription.gender,
      filename: jobdescription.filename,
      projectId: jobdescription.projectId,
      nationalities: jobdescription.nationalities,
      locations: jobdescription.locations,
      educationlevels: jobdescription.educationlevels,
      projectphaseActivities: jobdescription.projectphaseActivities,
      technicalDisciplines: jobdescription.technicalDisciplines,
      experiences: jobdescription.experiences,
      languages: jobdescription.languages,
      sectors: jobdescription.sectors,
      contentId: jobdescription.content?.id,
      dataContentType: jobdescription.content?.dataContentType,
      data: jobdescription.content?.data,
      text: jobdescription.content?.text
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobdescription = this.createFromForm();
    if (jobdescription.id !== undefined) {
      this.subscribeToSaveResponse(this.jobdescriptionService.update(jobdescription));
    } else {
      this.subscribeToSaveResponse(this.jobdescriptionService.create(jobdescription));
    }
  }

  private createFromForm(): IJobdescription {
    return {
      ...new Jobdescription(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      creation_dt: this.editForm.get(['creation_dt'])!.value
        ? moment(this.editForm.get(['creation_dt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      gender: this.editForm.get(['gender'])!.value,
      filename: this.editForm.get(['filename'])!.value,
      projectId: this.editForm.get(['projectId'])!.value,
      nationalities: this.editForm.get(['nationalities'])!.value,
      locations: this.editForm.get(['locations'])!.value,
      educationlevels: this.editForm.get(['educationlevels'])!.value,
      projectphaseActivities: this.editForm.get(['projectphaseActivities'])!.value,
      technicalDisciplines: this.editForm.get(['technicalDisciplines'])!.value,
      experiences: this.editForm.get(['experiences'])!.value,
      languages: this.editForm.get(['languages'])!.value,
      sectors: this.editForm.get(['sectors'])!.value,
      content: this.createFromFormContent(),
    };
  }

  private createFromFormContent(): IContent {
    return {
      ...new Content(),
      id: this.editForm.get(['contentId'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value,
      text: this.editForm.get(['text'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobdescription>>): void {
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
}
