import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Subscription, combineLatest } from 'rxjs';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AbstractEmobyComponent } from 'app/emoby/emoby.component';
import { ProjectService } from 'app/entities/project/project.service';
import { AccountService } from 'app/core/auth/account.service';
import { ContentService } from 'app/entities/content/content.service';

@Component({
  selector: 'jhi-jobdescription',
  templateUrl: './jobdescription.component.html',
  styleUrls: ['jobdescription.component.scss'],
  styles: [
    `
      :host ::ng-deep button {
        margin-right: 0.25em;
      }
    `,
  ],
})
export class JobdescriptionComponent extends AbstractEmobyComponent implements OnInit {
  jobdescriptions: IJobdescription[] = [];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate = 'id';
  ascending!: boolean;
  ngbPaginationPage = 1;
  eventSubscriber?: Subscription;
  visibleSidebar = false;
  constructor(
    protected jobdescriptionService: JobdescriptionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected http: HttpClient,
    protected projectService: ProjectService,
    protected accountService: AccountService,
    protected contentService: ContentService,
    protected dataUtils: JhiDataUtils
  ) {
    super(projectService, accountService);
  }
  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;

    this.jobdescriptionService
      .findAllJobDescriptions({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
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

  protected onSuccess(data: IJobdescription[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.jobdescriptions = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  public matchingSession(jobdescriptionId?:number): void {
    this.router.navigate(['jobdescription/', jobdescriptionId]);
  }

  public openFile(id?: number): void {
    if (id !== undefined) {
      this.contentService.find(id).subscribe(content => {
        const result = content.body;
        if (result != null && result.dataContentType !== undefined) {
          this.dataUtils.openFile(result.dataContentType, result.data);
        }
      });
    } else {
      alert('ID undefined');
    }
  }
}
