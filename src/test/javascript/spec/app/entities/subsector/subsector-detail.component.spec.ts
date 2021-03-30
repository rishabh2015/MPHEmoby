import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { SubsectorDetailComponent } from 'app/entities/subsector/subsector-detail.component';
import { Subsector } from 'app/shared/model/subsector.model';

describe('Component Tests', () => {
  describe('Subsector Management Detail Component', () => {
    let comp: SubsectorDetailComponent;
    let fixture: ComponentFixture<SubsectorDetailComponent>;
    const route = ({ data: of({ subsector: new Subsector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SubsectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubsectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubsectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subsector on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subsector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
