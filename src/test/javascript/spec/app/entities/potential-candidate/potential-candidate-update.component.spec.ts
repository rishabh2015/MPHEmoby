import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { PotentialCandidateUpdateComponent } from 'app/entities/potential-candidate/potential-candidate-update.component';
import { PotentialCandidateService } from 'app/entities/potential-candidate/potential-candidate.service';
import { PotentialCandidate } from 'app/shared/model/potential-candidate.model';

describe('Component Tests', () => {
  describe('PotentialCandidate Management Update Component', () => {
    let comp: PotentialCandidateUpdateComponent;
    let fixture: ComponentFixture<PotentialCandidateUpdateComponent>;
    let service: PotentialCandidateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [PotentialCandidateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PotentialCandidateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PotentialCandidateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PotentialCandidateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PotentialCandidate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PotentialCandidate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
