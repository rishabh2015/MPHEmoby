import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { EducationlevelUpdateComponent } from 'app/entities/educationlevel/educationlevel-update.component';
import { EducationlevelService } from 'app/entities/educationlevel/educationlevel.service';
import { Educationlevel } from 'app/shared/model/educationlevel.model';

describe('Component Tests', () => {
  describe('Educationlevel Management Update Component', () => {
    let comp: EducationlevelUpdateComponent;
    let fixture: ComponentFixture<EducationlevelUpdateComponent>;
    let service: EducationlevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [EducationlevelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EducationlevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EducationlevelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EducationlevelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Educationlevel(123);
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
        const entity = new Educationlevel();
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
