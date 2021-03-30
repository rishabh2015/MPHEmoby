import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobdescriptionService } from 'app/entities/jobdescription/jobdescription.service';
import { IJobdescription, Jobdescription } from 'app/shared/model/jobdescription.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('Jobdescription Service', () => {
    let injector: TestBed;
    let service: JobdescriptionService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobdescription;
    let expectedResult: IJobdescription | IJobdescription[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobdescriptionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Jobdescription(0, 'AAAAAAA', currentDate, Gender.M, 'AAAAAAA');
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

      it('should create a Jobdescription', () => {
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

        service.create(new Jobdescription()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Jobdescription', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            filename: 'BBBBBB',
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

      it('should return a list of Jobdescription', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            creation_dt: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            filename: 'BBBBBB',
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

      it('should delete a Jobdescription', () => {
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
