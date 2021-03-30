import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { CandidateLevelLanguageDetailComponent } from 'app/entities/candidate-level-language/candidate-level-language-detail.component';
import { CandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';

describe('Component Tests', () => {
  describe('CandidateLevelLanguage Management Detail Component', () => {
    let comp: CandidateLevelLanguageDetailComponent;
    let fixture: ComponentFixture<CandidateLevelLanguageDetailComponent>;
    const route = ({ data: of({ candidateLevelLanguage: new CandidateLevelLanguage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [CandidateLevelLanguageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CandidateLevelLanguageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CandidateLevelLanguageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load candidateLevelLanguage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.candidateLevelLanguage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
