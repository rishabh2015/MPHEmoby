import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmobyMphTestModule } from '../../../test.module';
import { JobdescriptionDetailComponent } from 'app/entities/jobdescription/jobdescription-detail.component';
import { Jobdescription } from 'app/shared/model/jobdescription.model';

describe('Component Tests', () => {
  describe('Jobdescription Management Detail Component', () => {
    let comp: JobdescriptionDetailComponent;
    let fixture: ComponentFixture<JobdescriptionDetailComponent>;
    const route = ({ data: of({ jobdescription: new Jobdescription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [JobdescriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobdescriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobdescriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobdescription on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobdescription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
