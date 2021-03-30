import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISector, Sector } from 'app/shared/model/sector.model';

type EntityResponseType = HttpResponse<ISector>;
type EntityArrayResponseType = HttpResponse<ISector[]>;

@Injectable({ providedIn: 'root' })
export class SectorService {
  public resourceUrl = SERVER_API_URL + 'api/sectors';

  constructor(protected http: HttpClient) {}

  create(sector: ISector): Observable<EntityResponseType> {
    return this.http.post<ISector>(this.resourceUrl, sector, { observe: 'response' });
  }

  update(sector: ISector): Observable<EntityResponseType> {
    return this.http.put<ISector>(this.resourceUrl, sector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  sectors(): Observable<Sector[]> {
    return this.http.get<Sector[]>(SERVER_API_URL + 'api/sectors');
  }
}
