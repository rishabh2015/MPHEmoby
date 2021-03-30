import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseActivityComponent } from 'app/entities/projectphase-activity/projectphase-activity.component';
import { ProjectphaseActivityService } from 'app/entities/projectphase-activity/projectphase-activity.service';
import { ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';

describe('Component Tests', () => {
  describe('ProjectphaseActivity Management Component', () => {
    let comp: ProjectphaseActivityComponent;
    let fixture: ComponentFixture<ProjectphaseActivityComponent>;
    let service: ProjectphaseActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseActivityComponent],
      })
        .overrideTemplate(ProjectphaseActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectphaseActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectphaseActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectphaseActivity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectphaseActivities && comp.projectphaseActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
