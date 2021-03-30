import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProjectphase, Projectphase } from 'app/shared/model/projectphase.model';

type EntityResponseType = HttpResponse<IProjectphase>;
type EntityArrayResponseType = HttpResponse<IProjectphase[]>;

@Injectable({ providedIn: 'root' })
export class ProjectphaseService {
  public resourceUrl = SERVER_API_URL + 'api/projectphases';

  constructor(protected http: HttpClient) {}

  create(projectphase: IProjectphase): Observable<EntityResponseType> {
    return this.http.post<IProjectphase>(this.resourceUrl, projectphase, { observe: 'response' });
  }

  update(projectphase: IProjectphase): Observable<EntityResponseType> {
    return this.http.put<IProjectphase>(this.resourceUrl, projectphase, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectphase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectphase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  projectPhases(): Observable<Projectphase[]> {
    return this.http.get<Projectphase[]>(SERVER_API_URL + 'api/projectphases');
  }
}
