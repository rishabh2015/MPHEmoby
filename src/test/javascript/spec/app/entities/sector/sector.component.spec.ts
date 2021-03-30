import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { SectorComponent } from 'app/entities/sector/sector.component';
import { SectorService } from 'app/entities/sector/sector.service';
import { Sector } from 'app/shared/model/sector.model';

describe('Component Tests', () => {
  describe('Sector Management Component', () => {
    let comp: SectorComponent;
    let fixture: ComponentFixture<SectorComponent>;
    let service: SectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SectorComponent],
      })
        .overrideTemplate(SectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sector(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sectors && comp.sectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
