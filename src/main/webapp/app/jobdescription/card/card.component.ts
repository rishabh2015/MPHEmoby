import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Data, ParamMap } from '@angular/router';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { PotentialCandidateService } from 'app/entities/potential-candidate/potential-candidate.service';
import { ITEMS_PER_PAGE_DETAIL } from 'app/shared/constants/pagination.constants';
import { Candidate } from 'app/shared/model/candidate.model';
import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { PotentialCandidate } from 'app/shared/model/potential-candidate.model';
import { Subscription, combineLatest } from 'rxjs';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MessageService } from 'primeng/api';
import { ContentService } from 'app/entities/content/content.service';

@Component({
  selector: 'jhi-card',
  templateUrl: './card.component.html',
  providers: [MessageService],
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  index = 0;
  @Input() mode!: string;
  @Output() successShortlistedEvent = new EventEmitter<number>();
  @Output() successPlacedEvent = new EventEmitter<number>();

  displayBasic = false;
  itemsPerPage = ITEMS_PER_PAGE_DETAIL;
  page!: number;
  eventSubscriber?: Subscription;
  predicate = 'id';
  ascending!: boolean;
  isSaving = false;
  ngbPaginationPage = 1;
  jobdescriptions: IJobdescription[] = [];
  matchingResults: PotentialCandidate[] = [];
  eagerload = false;
  totalItems = 0;
  percentMatching!: number | undefined;
  candidate!: Candidate | null;
  candidates: PotentialCandidate[] = [];
  p: number[] = [];
  commentaire = '';
  no_result = '';
  constructor(
    protected potentialCandidateService: PotentialCandidateService,
    protected candidateService: CandidateService,
    private route: ActivatedRoute,
    protected activatedRoute: ActivatedRoute,
    protected eventManager: JhiEventManager,
    protected dataUtils: JhiDataUtils,
    private messageService: MessageService,
    private contentService: ContentService
  ) { }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;
    const jobdescriptioId = this.route.snapshot.params['id'];
    this.potentialCandidateService
      .findAllPotentialCandidates(jobdescriptioId, {
        mode: this.mode,
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        // sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IJobdescription[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInJobdescriptions();
  }

  call(): void {
    this.handleNavigation();
    this.registerChangeInJobdescriptions();
  }

  registerChangeInJobdescriptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobdescriptionListModification', () => this.loadPage());
  }
  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      this.loadPage(pageNumber);
    }).subscribe();
  }
  sort(): string[] {
    const result = [this.predicate + ',' + 'desc'];
    return result;
  }
  protected onSuccess(data: PotentialCandidate[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    if (this.totalItems === 0) {
      this.no_result = '0 result';
    } else {
      this.no_result = '';
    }
    this.page = page;
    this.matchingResults = data || [];
    this.ngbPaginationPage = this.page;
  }
  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
  showBasicDialog(id: number | undefined, matching_percent: number | undefined): void {
    this.percentMatching = matching_percent;
    this.displayBasic = true;
    if (id !== undefined) {
      this.candidateService.find(id).subscribe(candidate => {
        this.candidate = candidate.body;
        this.commentaire = candidate.body?.comment;
      });
    }
  }
  setShortListed(candidateId?: number, currentCandidateShortlistedId?: number, currentCandidatePlacedId?: number): void {
    if (this.isSaving !== true) {
      this.isSaving = true;
      // eslint-disable-next-line
      console.log("On repasse à TRUE");
      const jobdescriptionId = this.route.snapshot.params['id'];
      // etoile vide => etoile rempli
      if ((currentCandidateShortlistedId === null || currentCandidateShortlistedId === 0) && this.mode === 'potentialcandidate') {
        // eslint-disable-next-line
        this.candidateService.setShortlisted({ candidateId: candidateId, jobdescriptionId: jobdescriptionId }).subscribe(
          () => {
            this.onSuccessShortlistedEvent(1);
            this.onShortlistedSuccess(candidateId || 0, jobdescriptionId);
            this.isSaving = false;
          },
          error => this.onSaveError(error)
          );
        } else if (
          currentCandidateShortlistedId + '' === jobdescriptionId &&
          this.mode === 'shortlisted' &&
          (currentCandidatePlacedId === null || currentCandidatePlacedId === 0)
          ) {
            // eslint-disable-next-line
            this.candidateService.setShortlisted({ candidateId: candidateId, jobdescriptionId: '' }).subscribe(
          () => {
            this.onSuccessShortlistedEvent(-1);
            this.onShortlistedSuccess(candidateId || 0, 0);
            this.isSaving = false;
          },
          error => this.onSaveError(error)
          );
        } else {
          this.isSaving = false;
        }
    }
  }
  protected onSaveError(error: Error): void {
    this.messageService.add({ severity: 'error', summary: 'Erreur', detail: '' + error.message });
    this.isSaving = false;
  }
  protected onShortlistedSuccess(candidateId: number, jobdescriptionId?: number): void {
    // Le cas ou on ajoute manuellement linfortmation candidate.shortlisted=jobdescriptionId
    if (jobdescriptionId !== undefined && jobdescriptionId > 0) {
      this.matchingResults.forEach(element => {
        if (element.candidate?.id === candidateId) {
          element.candidate.shortlistedId = jobdescriptionId;
        }
      });
    } else {
      // Le cas ou on retire manuellement linformation candite de matchingResults
      if (this.mode === 'shortlisted') {
        this.matchingResults = this.matchingResults.filter(item => item.candidate?.id !== candidateId);
        this.totalItems--;
      }
    }
  }
  setPlaced(candidateId?: number, currentCandidatePlacedId?: number): void {
    if (this.isSaving !== true) {
      this.isSaving = true;
      const jobdescriptionId = this.route.snapshot.params['id'];
      if (currentCandidatePlacedId === null || currentCandidatePlacedId === 0) {
        // eslint-disable-next-line
        this.candidateService.setPlaced({ candidateId: candidateId, jobdescriptionId: jobdescriptionId }).subscribe(
          () => {
            this.onSuccessPlacedEvent(1);
            this.onPlacedSuccess(candidateId || 0, jobdescriptionId);
            this.isSaving = false;
          },
          error => this.onSaveError(error)
        );
      } else if (currentCandidatePlacedId + '' === jobdescriptionId) {
        // eslint-disable-next-line
        this.candidateService.setPlaced({ candidateId: candidateId, jobdescriptionId: '' }).subscribe(
          () => {
            this.onSuccessPlacedEvent(-1);
            this.onPlacedSuccess(candidateId || 0, 0);
            this.isSaving = false;
          },
          error => this.onSaveError(error)
          );
        } else {
          this.isSaving = false;
      }
    }
  }
  protected onPlacedSuccess(candidateId: number, jobdescriptionId?: number): void {
    if (this.mode === 'shortlisted') {
      this.matchingResults.forEach(element => {
        if (element.candidate?.id === candidateId) {
          element.candidate.placedId = jobdescriptionId;
        }
      });
    } else {
      if (this.mode === 'placed') {
        this.matchingResults = this.matchingResults.filter(item => item.candidate?.id !== candidateId);
        this.totalItems--;
      }
    }
  }
  count(): number {
    return this.totalItems;
  }
  onSuccessShortlistedEvent(num: number): void {
    this.successShortlistedEvent.emit(num);
  }

  onSuccessPlacedEvent(num: number): void {
    this.successPlacedEvent.emit(num);
  }
  public setCandidateComment(candidateId?: number, comment?: string): void {
    this.candidateService.setCandidateComment(candidateId, comment).subscribe(() => { });
    this.commentaire = "";
  }
  public getCvByIdDocument(candidateId?: number): void {
    if (candidateId !== undefined) {
      this.contentService.getCvByIdDocument(candidateId).subscribe(
        content => {
          const result = content.body;
          if (result != null && result.dataContentType !== undefined) {
            this.dataUtils.downloadFile(result.dataContentType, result.data, result.filename);
          }
        },
        error => this.onSaveError(error)
      );
    }
  }
  public closeDialog(): void {
    this.displayBasic = false;
  }
}
