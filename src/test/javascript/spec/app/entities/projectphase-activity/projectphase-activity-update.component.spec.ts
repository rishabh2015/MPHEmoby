import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseActivityUpdateComponent } from 'app/entities/projectphase-activity/projectphase-activity-update.component';
import { ProjectphaseActivityService } from 'app/entities/projectphase-activity/projectphase-activity.service';
import { ProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';

describe('Component Tests', () => {
  describe('ProjectphaseActivity Management Update Component', () => {
    let comp: ProjectphaseActivityUpdateComponent;
    let fixture: ComponentFixture<ProjectphaseActivityUpdateComponent>;
    let service: ProjectphaseActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseActivityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProjectphaseActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectphaseActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectphaseActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectphaseActivity(123);
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
        const entity = new ProjectphaseActivity();
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
