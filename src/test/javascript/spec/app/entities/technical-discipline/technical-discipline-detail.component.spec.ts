import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { TechnicalDisciplineDetailComponent } from 'app/entities/technical-discipline/technical-discipline-detail.component';
import { TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';

describe('Component Tests', () => {
  describe('TechnicalDiscipline Management Detail Component', () => {
    let comp: TechnicalDisciplineDetailComponent;
    let fixture: ComponentFixture<TechnicalDisciplineDetailComponent>;
    const route = ({ data: of({ technicalDiscipline: new TechnicalDiscipline(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [TechnicalDisciplineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TechnicalDisciplineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnicalDisciplineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load technicalDiscipline on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.technicalDiscipline).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
