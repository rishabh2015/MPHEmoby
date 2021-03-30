import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { TechnicalDisciplineUpdateComponent } from 'app/entities/technical-discipline/technical-discipline-update.component';
import { TechnicalDisciplineService } from 'app/entities/technical-discipline/technical-discipline.service';
import { TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';

describe('Component Tests', () => {
  describe('TechnicalDiscipline Management Update Component', () => {
    let comp: TechnicalDisciplineUpdateComponent;
    let fixture: ComponentFixture<TechnicalDisciplineUpdateComponent>;
    let service: TechnicalDisciplineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [TechnicalDisciplineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TechnicalDisciplineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnicalDisciplineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnicalDisciplineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TechnicalDiscipline(123);
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
        const entity = new TechnicalDiscipline();
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
