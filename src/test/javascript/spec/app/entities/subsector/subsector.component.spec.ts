import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { SubsectorComponent } from 'app/entities/subsector/subsector.component';
import { SubsectorService } from 'app/entities/subsector/subsector.service';
import { Subsector } from 'app/shared/model/subsector.model';

describe('Component Tests', () => {
  describe('Subsector Management Component', () => {
    let comp: SubsectorComponent;
    let fixture: ComponentFixture<SubsectorComponent>;
    let service: SubsectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SubsectorComponent],
      })
        .overrideTemplate(SubsectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubsectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubsectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Subsector(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subsectors && comp.subsectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
