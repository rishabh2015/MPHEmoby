import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { LevelLanguageDetailComponent } from 'app/entities/level-language/level-language-detail.component';
import { LevelLanguage } from 'app/shared/model/level-language.model';

describe('Component Tests', () => {
  describe('LevelLanguage Management Detail Component', () => {
    let comp: LevelLanguageDetailComponent;
    let fixture: ComponentFixture<LevelLanguageDetailComponent>;
    const route = ({ data: of({ levelLanguage: new LevelLanguage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [LevelLanguageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LevelLanguageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LevelLanguageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load levelLanguage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.levelLanguage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
