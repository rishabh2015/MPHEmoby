import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { SectorsubsectorDetailComponent } from 'app/entities/sectorsubsector/sectorsubsector-detail.component';
import { Sectorsubsector } from 'app/shared/model/sectorsubsector.model';

describe('Component Tests', () => {
  describe('Sectorsubsector Management Detail Component', () => {
    let comp: SectorsubsectorDetailComponent;
    let fixture: ComponentFixture<SectorsubsectorDetailComponent>;
    const route = ({ data: of({ sectorsubsector: new Sectorsubsector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SectorsubsectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SectorsubsectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SectorsubsectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sectorsubsector on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sectorsubsector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
