import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { JobdescriptionUpdateComponent } from 'app/entities/jobdescription/jobdescription-update.component';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { Jobdescription } from 'app/shared/model/jobdescription.model';

describe('Component Tests', () => {
  describe('Jobdescription Management Update Component', () => {
    let comp: JobdescriptionUpdateComponent;
    let fixture: ComponentFixture<JobdescriptionUpdateComponent>;
    let service: JobdescriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [JobdescriptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobdescriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobdescriptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobdescriptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jobdescription(123);
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
        const entity = new Jobdescription();
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
