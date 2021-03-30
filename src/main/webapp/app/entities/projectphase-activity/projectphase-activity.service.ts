import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';

type EntityResponseType = HttpResponse<IProjectphaseActivity>;
type EntityArrayResponseType = HttpResponse<IProjectphaseActivity[]>;

@Injectable({ providedIn: 'root' })
export class ProjectphaseActivityService {
  public resourceUrl = SERVER_API_URL + 'api/projectphase-activities';

  constructor(protected http: HttpClient) {}

  create(projectphaseActivity: IProjectphaseActivity): Observable<EntityResponseType> {
    return this.http.post<IProjectphaseActivity>(this.resourceUrl, projectphaseActivity, { observe: 'response' });
  }

  update(projectphaseActivity: IProjectphaseActivity): Observable<EntityResponseType> {
    return this.http.put<IProjectphaseActivity>(this.resourceUrl, projectphaseActivity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectphaseActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectphaseActivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  projectPhaseActivity(): Observable<IProjectphaseActivity[]> {
    return this.http.get<IProjectphaseActivity[]>(this.resourceUrl);
  }
}
