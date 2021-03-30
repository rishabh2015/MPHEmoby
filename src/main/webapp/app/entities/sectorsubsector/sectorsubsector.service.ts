import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';

type EntityResponseType = HttpResponse<ISectorsubsector>;
type EntityArrayResponseType = HttpResponse<ISectorsubsector[]>;

@Injectable({ providedIn: 'root' })
export class SectorsubsectorService {
  public resourceUrl = SERVER_API_URL + 'api/sectorsubsectors';

  constructor(protected http: HttpClient) {}

  create(sectorsubsector: ISectorsubsector): Observable<EntityResponseType> {
    return this.http.post<ISectorsubsector>(this.resourceUrl, sectorsubsector, { observe: 'response' });
  }

  update(sectorsubsector: ISectorsubsector): Observable<EntityResponseType> {
    return this.http.put<ISectorsubsector>(this.resourceUrl, sectorsubsector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISectorsubsector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISectorsubsector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  sectorsubsectors(): Observable<ISectorsubsector[]> {
    return this.http.get<ISectorsubsector[]>(SERVER_API_URL + 'api/sectorsubsectors');
  }
}
