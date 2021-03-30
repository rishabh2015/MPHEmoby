import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { JobOpeningUpdateComponent } from 'app/entities/job-opening/job-opening-update.component';
import { JobOpeningService } from 'app/entities/job-opening/job-opening.service';
import { JobOpening } from 'app/shared/model/job-opening.model';

describe('Component Tests', () => {
  describe('JobOpening Management Update Component', () => {
    let comp: JobOpeningUpdateComponent;
    let fixture: ComponentFixture<JobOpeningUpdateComponent>;
    let service: JobOpeningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [JobOpeningUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobOpeningUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobOpeningUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobOpeningService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobOpening(123);
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
        const entity = new JobOpening();
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
