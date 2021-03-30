import { Component } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  // create seletor base for test override property
  selector: 'jhi-my-url',
  template: `
    <div>Component</div>
  `
})
export class AbstractEmobyComponent {
  
  public visibleSidebar = false;
  public projectId:number | undefined;
  public index = 0;

  constructor(protected projectService: ProjectService, protected accountService: AccountService) {
    const loginId = this.accountService.getLoginId();
    this.projectService.getFirstProjectByLoginId(loginId).subscribe(resp => (this.projectId = resp.body!.id ));
  }

}
