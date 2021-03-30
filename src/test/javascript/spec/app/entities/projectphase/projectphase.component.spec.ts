import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseComponent } from 'app/entities/projectphase/projectphase.component';
import { ProjectphaseService } from 'app/entities/projectphase/projectphase.service';
import { Projectphase } from 'app/shared/model/projectphase.model';

describe('Component Tests', () => {
  describe('Projectphase Management Component', () => {
    let comp: ProjectphaseComponent;
    let fixture: ComponentFixture<ProjectphaseComponent>;
    let service: ProjectphaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseComponent],
      })
        .overrideTemplate(ProjectphaseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectphaseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectphaseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Projectphase(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectphases && comp.projectphases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
