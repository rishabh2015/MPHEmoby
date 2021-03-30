import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { AbstractEmobyComponent } from 'app/emoby/emoby.component';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { PotentialCandidateService } from 'app/entities/potential-candidate/potential-candidate.service';
import { ProjectService } from 'app/entities/project/project.service';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CardComponent } from './card/card.component';

@Component({
  selector: 'jhi-jobdescription-detail',
  templateUrl: './jobdescription-detail.component.html',
  styleUrls: ['./jobdescription-detail.component.scss'],
})
export class JobdescriptionDetailComponent extends AbstractEmobyComponent {

  @ViewChild('potentialcandidate',  { static: true}) potentialcandidate!: CardComponent;

  @ViewChild('shortlisted',  { static: true}) shortlisted!: CardComponent;

  @ViewChild('placed',  { static: true}) placed!: CardComponent;
  
  totalPlaced=0;
  totalShortlisted=0;

  constructor(
    protected potentialCandidateService: PotentialCandidateService,
    protected projectService: ProjectService,
    protected accountService: AccountService,
    protected jobdescriptionService: JobdescriptionService,
    protected eventManager: JhiEventManager,
    protected router: Router,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected candidateService: CandidateService,
  ) {
    super(projectService, accountService);
  }

 potentialcandidateTabShortlistedSuccess(event: number):void {
    if(event===1) {
      // eslint-disable-next-line
      console.log("refresh shortlisted"); //
      this.shortlisted.call();
    }
    else if(event===-1) {
      // eslint-disable-next-line
      console.log("refresh shortlisted"); //
      this.shortlisted.call();
    }
  }
  shortlistedTabShortlistedSuccess(event: number):void {
    if(event===-1) {
      // eslint-disable-next-line
      console.log("refresh potentialcandidate"); //
      this.potentialcandidate.call();
    } else if(event===1) {
      // eslint-disable-next-line
      console.log("refresh placed"); //
      this.placed.call();
    }
  }
  shortlistedTabPlacedSuccess(event: number):void {
    if(event===-1) {
      // eslint-disable-next-line
      console.log("refresh placed"); //
      this.placed.call();
    } else if(event===1) {
      // eslint-disable-next-line
      console.log("refresh placed"); //
      this.placed.call();
    }
  }
  placedTabPlacedSuccess(event: number):void {
    if(event===-1) {
      // eslint-disable-next-line
      console.log("refresh shortlisted"); //
      this.shortlisted.call();
    } 
  }
}
