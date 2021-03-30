import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { MobyStatusUpdateComponent } from 'app/entities/moby-status/moby-status-update.component';
import { MobyStatusService } from 'app/entities/moby-status/moby-status.service';
import { MobyStatus } from 'app/shared/model/moby-status.model';

describe('Component Tests', () => {
  describe('MobyStatus Management Update Component', () => {
    let comp: MobyStatusUpdateComponent;
    let fixture: ComponentFixture<MobyStatusUpdateComponent>;
    let service: MobyStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [MobyStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MobyStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MobyStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MobyStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MobyStatus(123);
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
        const entity = new MobyStatus();
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
