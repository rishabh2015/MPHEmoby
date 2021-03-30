import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { TechnicalDisciplineComponent } from 'app/entities/technical-discipline/technical-discipline.component';
import { TechnicalDisciplineService } from 'app/entities/technical-discipline/technical-discipline.service';
import { TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';

describe('Component Tests', () => {
  describe('TechnicalDiscipline Management Component', () => {
    let comp: TechnicalDisciplineComponent;
    let fixture: ComponentFixture<TechnicalDisciplineComponent>;
    let service: TechnicalDisciplineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [TechnicalDisciplineComponent],
      })
        .overrideTemplate(TechnicalDisciplineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnicalDisciplineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnicalDisciplineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TechnicalDiscipline(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.technicalDisciplines && comp.technicalDisciplines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
