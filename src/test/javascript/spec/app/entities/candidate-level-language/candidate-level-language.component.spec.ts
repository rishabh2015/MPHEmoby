import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmobyMphTestModule } from '../../../test.module';
import { CandidateLevelLanguageComponent } from 'app/entities/candidate-level-language/candidate-level-language.component';
import { CandidateLevelLanguageService } from 'app/entities/candidate-level-language/candidate-level-language.service';
import { CandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';

describe('Component Tests', () => {
  describe('CandidateLevelLanguage Management Component', () => {
    let comp: CandidateLevelLanguageComponent;
    let fixture: ComponentFixture<CandidateLevelLanguageComponent>;
    let service: CandidateLevelLanguageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [CandidateLevelLanguageComponent],
      })
        .overrideTemplate(CandidateLevelLanguageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidateLevelLanguageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CandidateLevelLanguageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CandidateLevelLanguage(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.candidateLevelLanguages && comp.candidateLevelLanguages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
