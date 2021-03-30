import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { JobOpeningComponent } from 'app/entities/job-opening/job-opening.component';
import { JobOpeningService } from 'app/entities/job-opening/job-opening.service';
import { JobOpening } from 'app/shared/model/job-opening.model';

describe('Component Tests', () => {
  describe('JobOpening Management Component', () => {
    let comp: JobOpeningComponent;
    let fixture: ComponentFixture<JobOpeningComponent>;
    let service: JobOpeningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [JobOpeningComponent],
      })
        .overrideTemplate(JobOpeningComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobOpeningComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobOpeningService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JobOpening(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jobOpenings && comp.jobOpenings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
