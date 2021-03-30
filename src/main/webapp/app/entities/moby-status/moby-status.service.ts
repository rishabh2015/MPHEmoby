import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMobyStatus } from 'app/shared/model/moby-status.model';

type EntityResponseType = HttpResponse<IMobyStatus>;
type EntityArrayResponseType = HttpResponse<IMobyStatus[]>;

@Injectable({ providedIn: 'root' })
export class MobyStatusService {
  public resourceUrl = SERVER_API_URL + 'api/moby-statuses';

  constructor(protected http: HttpClient) {}

  create(mobyStatus: IMobyStatus): Observable<EntityResponseType> {
    return this.http.post<IMobyStatus>(this.resourceUrl, mobyStatus, { observe: 'response' });
  }

  update(mobyStatus: IMobyStatus): Observable<EntityResponseType> {
    return this.http.put<IMobyStatus>(this.resourceUrl, mobyStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMobyStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMobyStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
