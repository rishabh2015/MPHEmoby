import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { SectorsubsectorUpdateComponent } from 'app/entities/sectorsubsector/sectorsubsector-update.component';
import { SectorsubsectorService } from 'app/entities/sectorsubsector/sectorsubsector.service';
import { Sectorsubsector } from 'app/shared/model/sectorsubsector.model';

describe('Component Tests', () => {
  describe('Sectorsubsector Management Update Component', () => {
    let comp: SectorsubsectorUpdateComponent;
    let fixture: ComponentFixture<SectorsubsectorUpdateComponent>;
    let service: SectorsubsectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [SectorsubsectorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SectorsubsectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SectorsubsectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SectorsubsectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sectorsubsector(123);
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
        const entity = new Sectorsubsector();
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
