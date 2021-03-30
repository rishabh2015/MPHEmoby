import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobOpeningService } from 'app/entities/job-opening/job-opening.service';
import { IJobOpening, JobOpening } from 'app/shared/model/job-opening.model';

describe('Service Tests', () => {
  describe('JobOpening Service', () => {
    let injector: TestBed;
    let service: JobOpeningService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobOpening;
    let expectedResult: IJobOpening | IJobOpening[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobOpeningService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new JobOpening(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            delete_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a JobOpening', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            delete_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_date: currentDate,
            delete_date: currentDate,
          },
          returnedFromService
        );

        service.create(new JobOpening()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobOpening', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            guid: 'BBBBBB',
            text_clean: 'BBBBBB',
            file: 'BBBBBB',
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            delete_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_date: currentDate,
            delete_date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of JobOpening', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            guid: 'BBBBBB',
            text_clean: 'BBBBBB',
            file: 'BBBBBB',
            creation_date: currentDate.format(DATE_TIME_FORMAT),
            delete_date: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation_date: currentDate,
            delete_date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a JobOpening', () => {
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
