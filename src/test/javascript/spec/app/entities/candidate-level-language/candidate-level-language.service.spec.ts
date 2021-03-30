import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CandidateLevelLanguageService } from 'app/entities/candidate-level-language/candidate-level-language.service';
import { ICandidateLevelLanguage, CandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';

describe('Service Tests', () => {
  describe('CandidateLevelLanguage Service', () => {
    let injector: TestBed;
    let service: CandidateLevelLanguageService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidateLevelLanguage;
    let expectedResult: ICandidateLevelLanguage | ICandidateLevelLanguage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CandidateLevelLanguageService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CandidateLevelLanguage(0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CandidateLevelLanguage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CandidateLevelLanguage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CandidateLevelLanguage', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CandidateLevelLanguage', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CandidateLevelLanguage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
