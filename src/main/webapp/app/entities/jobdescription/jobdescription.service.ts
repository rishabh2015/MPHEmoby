import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobdescription } from 'app/shared/model/jobdescription.model';
import { AccountService } from 'app/core/auth/account.service';

type EntityResponseType = HttpResponse<IJobdescription>;
type EntityArrayResponseType = HttpResponse<IJobdescription[]>;

@Injectable({ providedIn: 'root' })
export class JobdescriptionService {
  public resourceUrl = SERVER_API_URL + 'api/jobdescriptions';
  public url = SERVER_API_URL + '/api/findAllJobDescriptionByUsername?loginId=';

  constructor(protected http: HttpClient, private accountService: AccountService) {}

  create(jobdescription: IJobdescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobdescription);
    return this.http
      .post<IJobdescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobdescription: IJobdescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobdescription);
    return this.http
      .put<IJobdescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobdescription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobdescription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  findAllJobDescriptions(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    const loginId = this.accountService.getLoginId();
    return this.http
      .get<IJobdescription[]>(this.url + loginId, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobdescription: IJobdescription): IJobdescription {
    const copy: IJobdescription = Object.assign({}, jobdescription, {
      creation_dt: jobdescription.creation_dt && jobdescription.creation_dt.isValid() ? jobdescription.creation_dt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creation_dt = res.body.creation_dt ? moment(res.body.creation_dt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobdescription: IJobdescription) => {
        jobdescription.creation_dt = jobdescription.creation_dt ? moment(jobdescription.creation_dt) : undefined;
      });
    }
    return res;
  }
}
