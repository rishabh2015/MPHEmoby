import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { Educationlevel, IEducationlevel } from 'app/shared/model/educationlevel.model';

type EntityResponseType = HttpResponse<IEducationlevel>;
type EntityArrayResponseType = HttpResponse<IEducationlevel[]>;

@Injectable({ providedIn: 'root' })
export class EducationlevelService {
  public resourceUrl = SERVER_API_URL + 'api/educationlevels';

  constructor(protected http: HttpClient) {}

  create(educationlevel: IEducationlevel): Observable<EntityResponseType> {
    return this.http.post<IEducationlevel>(this.resourceUrl, educationlevel, { observe: 'response' });
  }

  update(educationlevel: IEducationlevel): Observable<EntityResponseType> {
    return this.http.put<IEducationlevel>(this.resourceUrl, educationlevel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEducationlevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEducationlevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  educationsLevels(): Observable<Educationlevel[]> {
    return this.http.get<Educationlevel[]>(SERVER_API_URL + 'api/educationlevels');
  }
}
