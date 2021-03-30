import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CountryService } from 'app/entities/country/country.service';
import { ExperienceService } from 'app/entities/experience/experience.service';
import { SectorService } from 'app/entities/sector/sector.service';
import { Country } from 'app/shared/model/country.model';
import { IExperience } from 'app/shared/model/experience.model';
import { ISector, Sector } from 'app/shared/model/sector.model';
import { CandidateSearchService } from './candidate-search.service';
import { ISearchFilter, SearchFilter } from 'app/shared/model/ISearchFilter.model';
import { Jobopening } from 'app/shared/model/jobopening.model';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CandidateSearch } from 'app/shared/model/candidateSearch.model';

@Component({
  selector: 'jhi-candidate-search',
  templateUrl: './candidate-search.component.html',
  styleUrls: ['candidate-search.component.scss'],
})
export class CandidateSearchComponent implements OnInit {
  message: string;
  visibleSidebar!: boolean;
  selectedNationalities: Country[] = [];
  countries: Country[] = [];
  selectedExperiences: IExperience[] = [];
  experiences: IExperience[] = [];
  sectors: Sector[] = [];
  sectorsAll: Sector[] = [];
  selectedSectors: ISector[] = [];

  selectedJobopening?: CandidateSearch;
  jobopenings: Jobopening[] = [];
  jobopening!: Jobopening;
  experiencesId!: IExperience;
  sectorsId!: ISector;
  jobOpeningId!: Jobopening;
  candidates!: CandidateSearch[];
  visibleTable = false;
  selectedRows: CandidateSearch[] = [];
  cols = [
    { field: 'name', header: 'Name' },
    { field: 'date_of_birth', header: 'DOB' },
    { field: 'email', header: 'Email' },
    { field: 'phone_number', header: 'Phone No' },
    { field: 'nationality', header: 'Nationality' },
    { field: 'matching_percent', header: 'Matching %' },
    // { field: "resume", header: "Resume" },
    // { field: "status", header: "status" },
    // { field: "CV source", header: "CV source" }
  ];
  sidebarCols = [
    { sidebarField: 'name', header: 'Name' },
    { sidebarField: 'job_opening_title', header: 'Job opening title' },
  ];
  editForm = this.fb.group({
    firstName: [],
    keywords: [],
    lastName: [],
  });
  constructor(
    private countryService: CountryService,
    private experienceService: ExperienceService,
    private candidateSearchService: CandidateSearchService,
    private sectorService: SectorService,
    private fb: FormBuilder
  ) {
    this.message = 'CandidateSearchComponent message';
  }

  ngOnInit(): void {
    this.countryService.countries().subscribe(countries => {
      this.countries = countries;
    });
    this.experienceService.experiences().subscribe(experiences => {
      this.experiences = experiences;
    });
    this.sectorService.sectors().subscribe(sectors => {
      this.sectorsAll = sectors;
    });
  }
  filterJobopenings(title: string): void {
    this.candidateSearchService.jobopening(title).subscribe(jobopenings => {
      this.jobopenings = jobopenings;
    });
  }

  save(): void {
    this.visibleTable = false;
    this.candidates = [];

    const search = this.createFromForm();
    this.candidateSearchService.searchCandidates(search).subscribe((data: any) => {
      this.visibleTable = true;
      this.candidates = data.body;
    });
  }

  resetAllList(): void {
    this.selectedRows = [];
  }

  private createFromForm(): ISearchFilter {
    return {
      ...new SearchFilter(),
      experiencesId: this.selectedExperiences,
      firstName: this.editForm.get(['firstName'])!.value,
      jobOpeningId: this.selectedJobopening?.id,
      keywords: this.editForm.get(['keywords'])!.value,
      sectorsId: this.selectedSectors,
      lastName: this.editForm.get(['lastName'])!.value,
      nationalitiesId: this.selectedNationalities,
    };
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISearchFilter>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }
  protected onSaveSuccess(): void {}
  protected onSaveError(): void {}
  selectRow(value: any): void {
    // eslint-disable-next-line no-console
    console.log('============================', value);
  }
  public selectAllRow(checkvValue: any): void {
    if (checkvValue) {
      this.selectedRows = this.candidates.filter(value => value.id >= 0);
    } else {
      this.selectedRows = [];
    }
  }
}
