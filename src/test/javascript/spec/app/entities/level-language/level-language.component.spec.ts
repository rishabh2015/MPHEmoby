import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { LevelLanguageComponent } from 'app/entities/level-language/level-language.component';
import { LevelLanguageService } from 'app/entities/level-language/level-language.service';
import { LevelLanguage } from 'app/shared/model/level-language.model';

describe('Component Tests', () => {
  describe('LevelLanguage Management Component', () => {
    let comp: LevelLanguageComponent;
    let fixture: ComponentFixture<LevelLanguageComponent>;
    let service: LevelLanguageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [LevelLanguageComponent],
      })
        .overrideTemplate(LevelLanguageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LevelLanguageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LevelLanguageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LevelLanguage(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.levelLanguages && comp.levelLanguages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
