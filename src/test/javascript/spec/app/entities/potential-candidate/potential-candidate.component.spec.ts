import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { PotentialCandidateComponent } from 'app/entities/potential-candidate/potential-candidate.component';
import { PotentialCandidateService } from 'app/entities/potential-candidate/potential-candidate.service';
import { PotentialCandidate } from 'app/shared/model/potential-candidate.model';

describe('Component Tests', () => {
  describe('PotentialCandidate Management Component', () => {
    let comp: PotentialCandidateComponent;
    let fixture: ComponentFixture<PotentialCandidateComponent>;
    let service: PotentialCandidateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [PotentialCandidateComponent],
      })
        .overrideTemplate(PotentialCandidateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PotentialCandidateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PotentialCandidateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PotentialCandidate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.potentialCandidates && comp.potentialCandidates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
