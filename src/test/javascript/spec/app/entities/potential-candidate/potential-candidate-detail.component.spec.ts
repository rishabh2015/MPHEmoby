import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { PotentialCandidateDetailComponent } from 'app/entities/potential-candidate/potential-candidate-detail.component';
import { PotentialCandidate } from 'app/shared/model/potential-candidate.model';

describe('Component Tests', () => {
  describe('PotentialCandidate Management Detail Component', () => {
    let comp: PotentialCandidateDetailComponent;
    let fixture: ComponentFixture<PotentialCandidateDetailComponent>;
    const route = ({ data: of({ potentialCandidate: new PotentialCandidate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [PotentialCandidateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PotentialCandidateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PotentialCandidateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load potentialCandidate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.potentialCandidate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
