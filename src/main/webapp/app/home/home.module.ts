import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmobyMphSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { DashboardComponent } from 'app/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidebarModule } from 'primeng/sidebar';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TabViewModule } from 'primeng/tabview';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { MultiSelectModule } from 'primeng/multiselect';
import { CheckboxModule } from 'primeng/checkbox';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { SidebarComponent } from 'app/sidebar';
import { JobdescriptionComponent } from 'app/jobdescription/jobdescription.component';
import { BlockUIModule } from 'primeng/blockui';
import { JobdescriptionDetailComponent } from 'app/jobdescription/jobdescription-detail.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CardComponent } from 'app/jobdescription/card/card.component';
import { MessageModule } from 'primeng/message';
import { CandidateSearchComponent } from 'app/candidate-search';
import { AutoCompleteModule } from 'primeng/autocomplete';

@NgModule({
  imports: [
    EmobyMphSharedModule,
    BrowserAnimationsModule,
    SidebarModule,
    TableModule,
    ButtonModule,
    DialogModule,
    TabViewModule,
    DropdownModule,
    InputTextModule,
    FormsModule,
    VirtualScrollerModule,
    MultiSelectModule,
    CheckboxModule,
    BlockUIModule,
    TabViewModule,
    ButtonModule,
    CardModule,
    DialogModule,
    ToastModule,
    NgxPaginationModule,
    MessageModule,
    AutoCompleteModule,
    RouterModule.forChild([HOME_ROUTE]),
  ],
  declarations: [
    HomeComponent,
    DashboardComponent,
    JobdescriptionComponent,
    JobdescriptionDetailComponent,
    SidebarComponent,
    CardComponent,
    CandidateSearchComponent,
  ],
})
export class EmobyMphHomeModule {}
