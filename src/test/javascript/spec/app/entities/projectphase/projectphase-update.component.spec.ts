import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseUpdateComponent } from 'app/entities/projectphase/projectphase-update.component';
import { ProjectphaseService } from 'app/entities/projectphase/projectphase.service';
import { Projectphase } from 'app/shared/model/projectphase.model';

describe('Component Tests', () => {
  describe('Projectphase Management Update Component', () => {
    let comp: ProjectphaseUpdateComponent;
    let fixture: ComponentFixture<ProjectphaseUpdateComponent>;
    let service: ProjectphaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProjectphaseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectphaseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectphaseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Projectphase(123);
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
        const entity = new Projectphase();
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
