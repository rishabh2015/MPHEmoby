import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EmobyMphTestModule } from '../../../test.module';
import { JobOpeningDetailComponent } from 'app/entities/job-opening/job-opening-detail.component';
import { JobOpening } from 'app/shared/model/job-opening.model';

describe('Component Tests', () => {
  describe('JobOpening Management Detail Component', () => {
    let comp: JobOpeningDetailComponent;
    let fixture: ComponentFixture<JobOpeningDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ jobOpening: new JobOpening(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [JobOpeningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobOpeningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobOpeningDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load jobOpening on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobOpening).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
