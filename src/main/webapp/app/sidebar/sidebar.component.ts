import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AccountService } from 'app/core/auth/account.service';
import { CountryService } from 'app/entities/country/country.service';
import { EducationlevelService } from 'app/entities/educationlevel/educationlevel.service';
import { ExperienceService } from 'app/entities/experience/experience.service';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { ProjectService } from 'app/entities/project/project.service';
import { ProjectphaseActivityService } from 'app/entities/projectphase-activity/projectphase-activity.service';
import { ProjectphaseService } from 'app/entities/projectphase/projectphase.service';
import { SectorsubsectorService } from 'app/entities/sectorsubsector/sectorsubsector.service';
import { TechnicalDisciplineService } from 'app/entities/technical-discipline/technical-discipline.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Activity } from 'app/shared/model/activity.model';
import { Content, IContent } from 'app/shared/model/content.model';
import { Country } from 'app/shared/model/country.model';
import { Educationlevel } from 'app/shared/model/educationlevel.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { IExperience } from 'app/shared/model/experience.model';
import { IJobdescription, Jobdescription } from 'app/shared/model/jobdescription.model';
import { Project } from 'app/shared/model/project.model';
import { ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { Projectphase } from 'app/shared/model/projectphase.model';
import { Sector } from 'app/shared/model/sector.model';
import { Sectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { Subsector } from 'app/shared/model/subsector.model';
import { TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import * as moment from 'moment';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { PrimeNGConfig } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { EmobyService } from 'app/emoby/emoby.service';
import { Router } from '@angular/router';
import { SectorService } from 'app/entities/sector/sector.service';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  providers: [MessageService],
  styleUrls: ['sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  oneSectorAtLeast = false;
  isSaving = false;
  blocked = false;
  sectorSubsectorDialog = false;
  jobdescription?: IJobdescription;
  filename = '';
  countries: Country[] = [];
  experiences: IExperience[] = [];
  // languages: Language[] = [];
  selectedNationality: Country[] = [];
  selectedLocation: Country[] = [];
  selectedExperience: IExperience[] = [];
  // selectedLanguage: Language[] = [];
  technicalDisciplines: TechnicalDiscipline[] = [];
  selectedTechnicalDiscipline: TechnicalDiscipline[] = [];
  educationLevels: Educationlevel[] = [];
  selectedEducationLevel: Educationlevel[] = [];
  activities: Activity[] = [];
  projects: Project[] = [];
  selectedProject: Project[] = [];
  projectPhases: Projectphase[] = [];
  projectphaseActivity: ProjectphaseActivity[] = [];
  selectedProjectphaseActivity: ProjectphaseActivity[] = [];
  selectedProjectPhase: Projectphase[] = [];
  sectors: Sector[] = [];
  sectorsAll: Sector[] = [];
  selectedSector: Sector[] = [];
  selectedSubSector: Subsector[] = [];
  selectedSectorSubSector: Sectorsubsector[] = [];
  sectorsubsectors: Sectorsubsector[] = [];
  subsectors: Sectorsubsector[] = [];
  selectedGender?: { libelle: string; value: string };
  genders: { libelle: string; value: string }[] = [];
  index = 0;
  currentId: number | undefined;
  content: IContent | undefined;

  display = true;
  i = 0;

  @Input() visibleSidebar = false;
  @Input() projectId: number | undefined;
  @ViewChild('file') file?: ElementRef;

  editForm1 = this.fb.group({
    name: [null, [Validators.required]],
    data: [null, [Validators.required]],
    dataContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jobdescriptionService: JobdescriptionService,
    private accountService: AccountService,
    private primengConfig: PrimeNGConfig,
    private countryService: CountryService,
    private experienceService: ExperienceService,
    // private languageService: LanguageService,
    private technicalDisciplineService: TechnicalDisciplineService,
    private educationLevelService: EducationlevelService,
    private projecService: ProjectService,
    private projectPhaseService: ProjectphaseService,
    private projectphaseActivityService: ProjectphaseActivityService,
    private sectorsubsectorService: SectorsubsectorService,
    private sectorService: SectorService,
    private emobyService: EmobyService,
    private messageService: MessageService,
    private fb: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    // TODO UNSCUBSCRIBE
    // les secteurs sur lequel on est habilité
    this.sectors = this.accountService.getSectors();

    for (const item in Gender) {
      if (item != null) {
        this.genders.push({ libelle: item, value: item });
      }
    }
    this.countryService.countries().subscribe(countries => {
      this.countries = countries;
    });
    this.experienceService.experiences().subscribe(experiences => {
      this.experiences = experiences;
    });
    /* this.languageService.languages().subscribe(languages => {
      this.languages = languages;
    }); */
    this.technicalDisciplineService.technicalDisciplines().subscribe(technicalDisciplines => {
      this.technicalDisciplines = technicalDisciplines;
    });
    this.educationLevelService.educationsLevels().subscribe(educationLevels => {
      this.educationLevels = educationLevels;
    });
    this.projecService.projects().subscribe(projects => {
      this.projects = projects;
    });
    this.projectPhaseService.projectPhases().subscribe(projectPhases => {
      this.projectPhases = projectPhases;
    });
    this.sectorService.sectors().subscribe(sectors => {
      this.sectorsAll = sectors;

      this.sectorsAll.forEach(element => {
        if (!this.sectors.find(o => o.id === element.id)) {
          this.sectors.push(new Sector(element.id, element.code, element.libelle, true))
        } else {
          this.oneSectorAtLeast = true;
        }
      });
    });
    this.sectorsubsectorService.sectorsubsectors().subscribe(sectorsubsectors => {
      this.sectorsubsectors = sectorsubsectors;
    });
    this.projectphaseActivityService.projectPhaseActivity().subscribe(projectphaseActivity => {
      this.projectphaseActivity = projectphaseActivity;
    });
  }

  public showSidebar(): void {
    this.visibleSidebar = true;
  }

  public save1(): void {
    this.isSaving = true;
    this.jobdescription = this.createFromForm1();
    if (this.jobdescription.id !== undefined) {
      this.subscribeToSaveResponse(this.jobdescriptionService.update(this.jobdescription));
    } else {
      this.subscribeToSaveResponse(this.jobdescriptionService.create(this.jobdescription));
    }
  }

  public save2(): void {

    if (this.oneSectorAtLeast !== true) {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Please select at least one sector' });
      return;
    }

    if (this.activities.length > 0 && this.selectedProjectphaseActivity.length === 0) {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Please select at least one activity' });
      return;
    }

    this.isSaving = true;
    this.blocked = true;
    this.jobdescription = this.createFromForm2();

    if (this.jobdescription !== undefined && this.jobdescription.id !== undefined) {
      this.subscribeToSaveResponse2(this.jobdescriptionService.update(this.jobdescription));
    }
    this.index = 0;
    this.visibleSidebar = false;
    this.clearSideBar();
  }

  private createFromForm1(): IJobdescription {
    return {
      ...new Jobdescription(),
      id: this.currentId,
      name: this.editForm1.get(['name'])!.value,
      creation_dt: moment(new Date(), DATE_TIME_FORMAT),
      projectId: this.projectId,
      filename: this.filename,
      content: this.createFromFormContent1(),
    };
  }

  private createFromFormContent1(): IContent {
    return {
      ...new Content(),
      dataContentType: this.editForm1.get(['dataContentType'])!.value,
      data: this.editForm1.get(['data'])!.value,
    };
  }

  private createFromForm2(): IJobdescription {
    let myGender: Gender | undefined;
    if (this.selectedGender?.libelle === 'F') {
      myGender = Gender.F;
    } else if (this.selectedGender?.libelle === 'M') {
      myGender = Gender.M;
    }

    return {
      ...new Jobdescription(),
      id: this.currentId,
      name: this.jobdescription?.name,
      creation_dt: this.jobdescription?.creation_dt,
      gender: myGender,
      filename: this.jobdescription?.filename,
      projectId: this.projectId,
      nationalities: this.selectedNationality,
      locations: this.selectedLocation,
      educationlevels: this.selectedEducationLevel,
      projectphaseActivities: this.selectedProjectphaseActivity,
      technicalDisciplines: this.selectedTechnicalDiscipline,
      experiences: this.selectedExperience,
      // languages: this.selectedLanguage,
      sectors: this.selectedSector,
      content: this.content,
    };
  }

  public handleChange(): void {
    if (this.index !== 0) {
      this.index = 0;
    }
  }
  public showSectorSubsectorDialog(): void {
    this.sectorSubsectorDialog = true;
  }

  public hideSectorSubsectorDialog(): void {
    this.sectorSubsectorDialog = false;
  }

  public saveSectorSubsectorDialog(): void {
    this.sectorSubsectorDialog = false;
    this.selectedSubSector.forEach(element => {
      this.selectedSectorSubSector.push(element);
    });
    this.sectorSubsectorDialog = false;
  }

  public updateSubSector(): void {
    this.subsectors = [];
    this.sectorsubsectors.forEach(element => {
      this.selectedSector.forEach(sector => {
        if (element.sectorId === sector.id) {
          this.subsectors.push(element);
        }
      });
    });
  }

  public updateActivities(): void {
    this.activities = [];

    this.selectedProjectPhase.forEach(selected => {
      this.projectphaseActivity.forEach(projectphaseactivity => {
        if (selected.id === projectphaseactivity.projectphaseId) {
          this.activities.push(projectphaseactivity);
        }
      });
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobdescription>>): void {
    result.subscribe(
      value => this.onSaveSuccess(value),
      error => this.onSaveError(error)
    );
  }

  protected subscribeToSaveResponse2(result: Observable<HttpResponse<IJobdescription>>): void {
    result.subscribe(
      value => this.onSaveSuccess2(value),
      error => this.onSaveError(error)
    );
  }

  protected onSaveSuccess(value: HttpResponse<IJobdescription>): void {
    this.currentId = value.body?.id;
    this.content = value.body?.content;
    this.index = 1;
    this.isSaving = false;
  }

  protected susbcribeToCallEmoby(result: Observable<HttpResponse<number>>): void {
    result.subscribe(id => {
      console.log("success call emoby : "+id.body); // eslint-disable-line
      this.isSaving = false;
      this.blocked = false;
      this.currentId = undefined;
      this.router.navigateByUrl('jobdescription/' + id.body);
    },
      error => this.onSaveError(error));
  }

  protected onSaveSuccess2(value: HttpResponse<IJobdescription>): void {
    if (value.body !== null && value.body.id !== null && value.body.id !== undefined) {
      this.susbcribeToCallEmoby(this.emobyService.callEmoby(value.body.id, value.body.sectors));
    } else {
      this.isSaving = false;
      console.log("Error no body"); // eslint-disable-line
      this.blocked = false;
      this.visibleSidebar = false;
      this.currentId = undefined;
      this.clearSideBar();
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'value.body undefined' });
    }
  }

  protected onSaveError(error: Error): void {
    console.log("onSaveError : " + error.message); // eslint-disable-line
    this.isSaving = false;
    this.blocked = false;
    this.visibleSidebar = false;
    this.currentId = undefined;
    this.clearSideBar();
    this.messageService.add({ severity: 'error', summary: 'Erreur', detail: '' + error.message });
  }

  public clearSideBar(): void {
    this.editForm1.patchValue({ name: null });
    this.editForm1.patchValue({ data: null });
    this.editForm1.patchValue({ dataContentType: null });

    if (this.file !== undefined) {
      this.file.nativeElement.value = null;
    }

    this.selectedGender = undefined;
    this.selectedNationality = [];
    this.selectedLocation = [];
    this.selectedProjectPhase = [];
    this.selectedProject = [];
    this.selectedProjectphaseActivity = [];
    this.selectedEducationLevel = [];
    this.selectedExperience = [];
    //  this.selectedLanguage = [];
    this.selectedTechnicalDiscipline = [];
    this.selectedSector = [];
  }

  public onShowSidebar(): void {
    if (this.index === 0) {
      this.clearSideBar();
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.filename = (<HTMLInputElement>event.target).files?.item(0)?.name!;

    this.dataUtils.loadFileToForm(event, this.editForm1, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('emobyMphApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }
}
