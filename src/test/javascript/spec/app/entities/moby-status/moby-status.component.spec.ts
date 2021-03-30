import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { MobyStatusComponent } from 'app/entities/moby-status/moby-status.component';
import { MobyStatusService } from 'app/entities/moby-status/moby-status.service';
import { MobyStatus } from 'app/shared/model/moby-status.model';

describe('Component Tests', () => {
  describe('MobyStatus Management Component', () => {
    let comp: MobyStatusComponent;
    let fixture: ComponentFixture<MobyStatusComponent>;
    let service: MobyStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [MobyStatusComponent],
      })
        .overrideTemplate(MobyStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MobyStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MobyStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MobyStatus(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mobyStatuses && comp.mobyStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
