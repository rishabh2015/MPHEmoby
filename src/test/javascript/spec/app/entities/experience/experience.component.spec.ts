import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { ExperienceComponent } from 'app/entities/experience/experience.component';
import { ExperienceService } from 'app/entities/experience/experience.service';
import { Experience } from 'app/shared/model/experience.model';

describe('Component Tests', () => {
  describe('Experience Management Component', () => {
    let comp: ExperienceComponent;
    let fixture: ComponentFixture<ExperienceComponent>;
    let service: ExperienceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ExperienceComponent],
      })
        .overrideTemplate(ExperienceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExperienceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExperienceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Experience(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.experiences && comp.experiences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
