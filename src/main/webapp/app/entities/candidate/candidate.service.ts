import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICandidate } from 'app/shared/model/candidate.model';

type EntityResponseType = HttpResponse<ICandidate>;
type EntityArrayResponseType = HttpResponse<ICandidate[]>;

@Injectable({ providedIn: 'root' })
export class CandidateService {
  public resourceUrl = SERVER_API_URL + 'api/candidates';
  public setShortlistedUrl = SERVER_API_URL + '/api/setShortlisted';
  public setPlacedUrl = SERVER_API_URL + '/api/setPlaced';
  public setCandidateCommentUrl = SERVER_API_URL + '/api/setCandidateComment';
  constructor(protected http: HttpClient) {}

  create(candidate: ICandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidate);
    return this.http
      .post<ICandidate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(candidate: ICandidate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidate);
    return this.http
      .put<ICandidate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICandidate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  setShortlisted(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidate>(this.setShortlistedUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  setPlaced(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidate>(this.setPlacedUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  setCandidateComment(candiadteId?: number, comment?: string): Observable<EntityResponseType> {
    const body = {
      id: candiadteId,
      // eslint-disable-next-line
      comment: comment,
    };
    return this.http
      .post<ICandidate>(this.setCandidateCommentUrl, body, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(candidate: ICandidate): ICandidate {
    const copy: ICandidate = Object.assign({}, candidate, {
      date_of_birth: candidate.date_of_birth && candidate.date_of_birth.isValid() ? candidate.date_of_birth.format(DATE_FORMAT) : undefined,
      creation_date: candidate.creation_date && candidate.creation_date.isValid() ? candidate.creation_date.toJSON() : undefined,
      update_date: candidate.update_date && candidate.update_date.isValid() ? candidate.update_date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date_of_birth = res.body.date_of_birth ? moment(res.body.date_of_birth) : undefined;
      res.body.creation_date = res.body.creation_date ? moment(res.body.creation_date) : undefined;
      res.body.update_date = res.body.update_date ? moment(res.body.update_date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((candidate: ICandidate) => {
        candidate.date_of_birth = candidate.date_of_birth ? moment(candidate.date_of_birth) : undefined;
        candidate.creation_date = candidate.creation_date ? moment(candidate.creation_date) : undefined;
        candidate.update_date = candidate.update_date ? moment(candidate.update_date) : undefined;
      });
    }
    return res;
  }
}
