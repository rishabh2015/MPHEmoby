import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'experience',
        loadChildren: () => import('./experience/experience.module').then(m => m.EmobyMphExperienceModule),
      },
      {
        path: 'educationlevel',
        loadChildren: () => import('./educationlevel/educationlevel.module').then(m => m.EmobyMphEducationlevelModule),
      },
      {
        path: 'language',
        loadChildren: () => import('./language/language.module').then(m => m.EmobyMphLanguageModule),
      },
      {
        path: 'sector',
        loadChildren: () => import('./sector/sector.module').then(m => m.EmobyMphSectorModule),
      },
      {
        path: 'job',
        loadChildren: () => import('./jobdescription/jobdescription.module').then(m => m.EmobyMphJobdescriptionModule),
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.EmobyMphProjectModule),
      },
      {
        path: 'projectphase',
        loadChildren: () => import('./projectphase/projectphase.module').then(m => m.EmobyMphProjectphaseModule),
      },
      {
        path: 'subsector',
        loadChildren: () => import('./subsector/subsector.module').then(m => m.EmobyMphSubsectorModule),
      },
      {
        path: 'sectorsubsector',
        loadChildren: () => import('./sectorsubsector/sectorsubsector.module').then(m => m.EmobyMphSectorsubsectorModule),
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.EmobyMphActivityModule),
      },
      {
        path: 'projectphase-activity',
        loadChildren: () => import('./projectphase-activity/projectphase-activity.module').then(m => m.EmobyMphProjectphaseActivityModule),
      },
      {
        path: 'technical-discipline',
        loadChildren: () => import('./technical-discipline/technical-discipline.module').then(m => m.EmobyMphTechnicalDisciplineModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.EmobyMphCountryModule),
      },
      {
        path: 'content',
        loadChildren: () => import('./content/content.module').then(m => m.EmobyMphContentModule),
      },
      {
        path: 'potential-candidate',
        loadChildren: () => import('./potential-candidate/potential-candidate.module').then(m => m.EmobyMphPotentialCandidateModule),
      },
      {
        path: 'level-language',
        loadChildren: () => import('./level-language/level-language.module').then(m => m.EmobyMphLevelLanguageModule),
      },
      {
        path: 'candidate',
        loadChildren: () => import('./candidate/candidate.module').then(m => m.EmobyMphCandidateModule),
      },
      {
        path: 'candidate-level-language',
        loadChildren: () =>
          import('./candidate-level-language/candidate-level-language.module').then(m => m.EmobyMphCandidateLevelLanguageModule),
      },
      {
        path: 'job-opening',
        loadChildren: () => import('./job-opening/job-opening.module').then(m => m.EmobyMphJobOpeningModule),
      },
      {
        path: 'moby-status',
        loadChildren: () => import('./moby-status/moby-status.module').then(m => m.EmobyMphMobyStatusModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EmobyMphEntityModule {}
