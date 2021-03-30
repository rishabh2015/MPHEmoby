import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITechnicalDiscipline, TechnicalDiscipline } from 'app/shared/model/technical-discipline.model';

type EntityResponseType = HttpResponse<ITechnicalDiscipline>;
type EntityArrayResponseType = HttpResponse<ITechnicalDiscipline[]>;

@Injectable({ providedIn: 'root' })
export class TechnicalDisciplineService {
  public resourceUrl = SERVER_API_URL + 'api/technical-disciplines';

  constructor(protected http: HttpClient) {}

  create(technicalDiscipline: ITechnicalDiscipline): Observable<EntityResponseType> {
    return this.http.post<ITechnicalDiscipline>(this.resourceUrl, technicalDiscipline, { observe: 'response' });
  }

  update(technicalDiscipline: ITechnicalDiscipline): Observable<EntityResponseType> {
    return this.http.put<ITechnicalDiscipline>(this.resourceUrl, technicalDiscipline, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITechnicalDiscipline>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITechnicalDiscipline[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  technicalDisciplines(): Observable<TechnicalDiscipline[]> {
    return this.http.get<TechnicalDiscipline[]>(SERVER_API_URL + 'api/technical-disciplines');
  }
}
