import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EmobyMphTestModule } from '../../../test.module';
import { CandidateDetailComponent } from 'app/entities/candidate/candidate-detail.component';
import { Candidate } from 'app/shared/model/candidate.model';

describe('Component Tests', () => {
  describe('Candidate Management Detail Component', () => {
    let comp: CandidateDetailComponent;
    let fixture: ComponentFixture<CandidateDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ candidate: new Candidate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EmobyMphTestModule],
        declarations: [CandidateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CandidateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CandidateDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load candidate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.candidate).toEqual(jasmine.objectContaining({ id: 123 }));
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
