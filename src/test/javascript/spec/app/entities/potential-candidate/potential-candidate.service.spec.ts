import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PotentialCandidateService } from 'app/entities/potential-candidate/potential-candidate.service';
import { IPotentialCandidate, PotentialCandidate } from 'app/shared/model/potential-candidate.model';

describe('Service Tests', () => {
  describe('PotentialCandidate Service', () => {
    let injector: TestBed;
    let service: PotentialCandidateService;
    let httpMock: HttpTestingController;
    let elemDefault: IPotentialCandidate;
    let expectedResult: IPotentialCandidate | IPotentialCandidate[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PotentialCandidateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PotentialCandidate(0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PotentialCandidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_dt: currentDate,
          },
          returnedFromService
        );

        service.create(new PotentialCandidate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PotentialCandidate', () => {
        const returnedFromService = Object.assign(
          {
            matching_percent: 1,
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_dt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PotentialCandidate', () => {
        const returnedFromService = Object.assign(
          {
            matching_percent: 1,
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_dt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PotentialCandidate', () => {
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
