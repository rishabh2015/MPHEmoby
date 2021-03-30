import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { EmobyMphCoreModule } from 'app/core/core.module';
import { EmobyMphAppRoutingModule } from './app-routing.module';
import { EmobyMphHomeModule } from './home/home.module';
import { EmobyMphEntityModule } from './entities/entity.module';
import { EmobyMphAppDashboardModule } from './dashboard/dashboard.module';
import { EmobyMphAppLoginModule } from './shared/login/login.module';
import { EmobyMphAppJobdescriptionModule } from './jobdescription/jobdescription.module';
import { EmobyMphAppSidebarModule } from './sidebar/sidebar.module';
import { EmobyMphAppCandidateSearchModule } from './candidate-search/candidate-search.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmobyMphAppCardModule } from './jobdescription/card/card.module';

@NgModule({
  imports: [
    BrowserModule,
    EmobyMphSharedModule,
    EmobyMphCoreModule,
    EmobyMphHomeModule,
    EmobyMphAppDashboardModule,
    EmobyMphAppLoginModule,
    EmobyMphAppJobdescriptionModule,
    EmobyMphAppSidebarModule,
    EmobyMphAppCandidateSearchModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EmobyMphEntityModule,
    EmobyMphAppRoutingModule,
    BrowserAnimationsModule,
    EmobyMphAppCardModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class EmobyMphAppModule {}
