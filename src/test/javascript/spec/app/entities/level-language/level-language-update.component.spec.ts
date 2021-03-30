import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { LevelLanguageUpdateComponent } from 'app/entities/level-language/level-language-update.component';
import { LevelLanguageService } from 'app/entities/level-language/level-language.service';
import { LevelLanguage } from 'app/shared/model/level-language.model';

describe('Component Tests', () => {
  describe('LevelLanguage Management Update Component', () => {
    let comp: LevelLanguageUpdateComponent;
    let fixture: ComponentFixture<LevelLanguageUpdateComponent>;
    let service: LevelLanguageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [LevelLanguageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LevelLanguageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LevelLanguageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LevelLanguageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LevelLanguage(123);
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
        const entity = new LevelLanguage();
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
