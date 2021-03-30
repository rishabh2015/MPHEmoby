import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { SectorsubsectorComponent } from 'app/entities/sectorsubsector/sectorsubsector.component';
import { SectorsubsectorService } from 'app/entities/sectorsubsector/sectorsubsector.service';
import { Sectorsubsector } from 'app/shared/model/sectorsubsector.model';

describe('Component Tests', () => {
  describe('Sectorsubsector Management Component', () => {
    let comp: SectorsubsectorComponent;
    let fixture: ComponentFixture<SectorsubsectorComponent>;
    let service: SectorsubsectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SectorsubsectorComponent],
      })
        .overrideTemplate(SectorsubsectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SectorsubsectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SectorsubsectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sectorsubsector(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sectorsubsectors && comp.sectorsubsectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
