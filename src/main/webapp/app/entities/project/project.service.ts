import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProject, Project } from 'app/shared/model/project.model';

type EntityResponseType = HttpResponse<IProject>;
type EntityArrayResponseType = HttpResponse<IProject[]>;

@Injectable({ providedIn: 'root' })
export class ProjectService {
  public resourceUrl = SERVER_API_URL + 'api/projects';
  public resourceGetFirstProjectByLoginIdUrl = SERVER_API_URL + 'api/getFirstProjectByLoginId';

  constructor(protected http: HttpClient) {}

  create(project: IProject): Observable<EntityResponseType> {
    return this.http.post<IProject>(this.resourceUrl, project, { observe: 'response' });
  }

  update(project: IProject): Observable<EntityResponseType> {
    return this.http.put<IProject>(this.resourceUrl, project, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  
  projects(): Observable<Project[]> {
    return this.http.get<Project[]>(SERVER_API_URL + 'api/projects');
  }
  
  getFirstProjectByLoginId(id: number): Observable<EntityResponseType> {
    return this.http.get<IProject>(`${this.resourceUrl}/getFirstProjectByLoginId/${id}`, { observe: 'response' });
  }
}
