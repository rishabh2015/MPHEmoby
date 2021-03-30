import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { EducationlevelDetailComponent } from 'app/entities/educationlevel/educationlevel-detail.component';
import { Educationlevel } from 'app/shared/model/educationlevel.model';

describe('Component Tests', () => {
  describe('Educationlevel Management Detail Component', () => {
    let comp: EducationlevelDetailComponent;
    let fixture: ComponentFixture<EducationlevelDetailComponent>;
    const route = ({ data: of({ educationlevel: new Educationlevel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [EducationlevelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EducationlevelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EducationlevelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load educationlevel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.educationlevel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
