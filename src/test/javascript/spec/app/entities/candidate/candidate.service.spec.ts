import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { ICandidate, Candidate } from 'app/shared/model/candidate.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('Candidate Service', () => {
    let injector: TestBed;
    let service: CandidateService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidate;
    let expectedResult: ICandidate | ICandidate[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CandidateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Candidate(
        0,
        Gender.F,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date_of_birth: currentDate.format(DATE_FORMAT),
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            update_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date_of_birth: currentDate.format(DATE_FORMAT),
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            update_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_of_birth: currentDate,
            creation_date: currentDate,
            update_date: currentDate,
          },
          returnedFromService
        );

        service.create(new Candidate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Candidate', () => {
        const returnedFromService = Object.assign(
          {
            gender: 'BBBBBB',
            last_name: 'BBBBBB',
            first_name: 'BBBBBB',
            date_of_birth: currentDate.format(DATE_FORMAT),
            text_clean: 'BBBBBB',
            guid: 'BBBBBB',
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            update_date: currentDate.format(DATE_TIME_FORMAT),
            comment: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_of_birth: currentDate,
            creation_date: currentDate,
            update_date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Candidate', () => {
        const returnedFromService = Object.assign(
          {
            gender: 'BBBBBB',
            last_name: 'BBBBBB',
            first_name: 'BBBBBB',
            date_of_birth: currentDate.format(DATE_FORMAT),
            text_clean: 'BBBBBB',
            guid: 'BBBBBB',
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            update_date: currentDate.format(DATE_TIME_FORMAT),
            comment: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date_of_birth: currentDate,
            creation_date: currentDate,
            update_date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Candidate', () => {
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
