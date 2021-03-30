import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  styles: [
    `
      :host ::ng-deep button {
        margin-right: 0.25em;
      }
    `,
  ],
})
export class DashboardComponent implements OnInit {
  visibleSidebar = false;
  projectId:number | undefined;
  index = 0;

  constructor(private projectService: ProjectService,
    private accountService: AccountService,
  ) {
  }

  ngOnInit(): void {
    const loginId = this.accountService.getLoginId();
    this.projectService.getFirstProjectByLoginId(loginId).subscribe(resp => (this.projectId = resp.body!.id ));
  }
}
