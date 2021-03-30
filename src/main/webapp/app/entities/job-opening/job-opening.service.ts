import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobOpening } from 'app/shared/model/job-opening.model';

type EntityResponseType = HttpResponse<IJobOpening>;
type EntityArrayResponseType = HttpResponse<IJobOpening[]>;

@Injectable({ providedIn: 'root' })
export class JobOpeningService {
  public resourceUrl = SERVER_API_URL + 'api/job-openings';

  constructor(protected http: HttpClient) {}

  create(jobOpening: IJobOpening): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobOpening);
    return this.http
      .post<IJobOpening>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobOpening: IJobOpening): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobOpening);
    return this.http
      .put<IJobOpening>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobOpening>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobOpening[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobOpening: IJobOpening): IJobOpening {
    const copy: IJobOpening = Object.assign({}, jobOpening, {
      creation_date: jobOpening.creation_date && jobOpening.creation_date.isValid() ? jobOpening.creation_date.toJSON() : undefined,
      delete_date: jobOpening.delete_date && jobOpening.delete_date.isValid() ? jobOpening.delete_date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creation_date = res.body.creation_date ? moment(res.body.creation_date) : undefined;
      res.body.delete_date = res.body.delete_date ? moment(res.body.delete_date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobOpening: IJobOpening) => {
        jobOpening.creation_date = jobOpening.creation_date ? moment(jobOpening.creation_date) : undefined;
        jobOpening.delete_date = jobOpening.delete_date ? moment(jobOpening.delete_date) : undefined;
      });
    }
    return res;
  }
}
