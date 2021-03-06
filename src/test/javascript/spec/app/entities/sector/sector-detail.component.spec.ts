import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { SectorDetailComponent } from 'app/entities/sector/sector-detail.component';
import { Sector } from 'app/shared/model/sector.model';

describe('Component Tests', () => {
  describe('Sector Management Detail Component', () => {
    let comp: SectorDetailComponent;
    let fixture: ComponentFixture<SectorDetailComponent>;
    const route = ({ data: of({ sector: new Sector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sector on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
