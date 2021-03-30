import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseActivityDetailComponent } from 'app/entities/projectphase-activity/projectphase-activity-detail.component';
import { ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';

describe('Component Tests', () => {
  describe('ProjectphaseActivity Management Detail Component', () => {
    let comp: ProjectphaseActivityDetailComponent;
    let fixture: ComponentFixture<ProjectphaseActivityDetailComponent>;
    const route = ({ data: of({ projectphaseActivity: new ProjectphaseActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProjectphaseActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectphaseActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectphaseActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectphaseActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
