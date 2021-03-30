import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { ProjectphaseDetailComponent } from 'app/entities/projectphase/projectphase-detail.component';
import { Projectphase } from 'app/shared/model/projectphase.model';

describe('Component Tests', () => {
  describe('Projectphase Management Detail Component', () => {
    let comp: ProjectphaseDetailComponent;
    let fixture: ComponentFixture<ProjectphaseDetailComponent>;
    const route = ({ data: of({ projectphase: new Projectphase(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [ProjectphaseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProjectphaseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectphaseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectphase on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectphase).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
