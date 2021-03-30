import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPotentialCandidate } from 'app/shared/model/potential-candidate.model';

type EntityResponseType = HttpResponse<IPotentialCandidate>;
type EntityArrayResponseType = HttpResponse<IPotentialCandidate[]>;

@Injectable({ providedIn: 'root' })
export class PotentialCandidateService {
  public resourceUrl = SERVER_API_URL + 'api/potential-candidates';
  public potentailCandidateUrl = SERVER_API_URL + '/api/potential-candidates/findAllPotentialCandidateByJobdescriptionId?jobdescriptionId=';

  constructor(protected http: HttpClient) {}

  create(potentialCandidate: IPotentialCandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(potentialCandidate);
    return this.http
      .post<IPotentialCandidate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(potentialCandidate: IPotentialCandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(potentialCandidate);
    return this.http
      .put<IPotentialCandidate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPotentialCandidate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPotentialCandidate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  findAllPotentialCandidates(jobdescriptionId:number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPotentialCandidate[]>(this.potentailCandidateUrl + jobdescriptionId, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(potentialCandidate: IPotentialCandidate): IPotentialCandidate {
    const copy: IPotentialCandidate = Object.assign({}, potentialCandidate, {
      creation_dt:
        potentialCandidate.creation_dt && potentialCandidate.creation_dt.isValid() ? potentialCandidate.creation_dt.toJSON() : undefined,
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
      res.body.forEach((potentialCandidate: IPotentialCandidate) => {
        potentialCandidate.creation_dt = potentialCandidate.creation_dt ? moment(potentialCandidate.creation_dt) : undefined;
      });
    }
    return res;
  }
}
