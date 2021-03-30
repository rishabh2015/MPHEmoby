import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILevelLanguage } from 'app/shared/model/level-language.model';

type EntityResponseType = HttpResponse<ILevelLanguage>;
type EntityArrayResponseType = HttpResponse<ILevelLanguage[]>;

@Injectable({ providedIn: 'root' })
export class LevelLanguageService {
  public resourceUrl = SERVER_API_URL + 'api/level-languages';

  constructor(protected http: HttpClient) {}

  create(levelLanguage: ILevelLanguage): Observable<EntityResponseType> {
    return this.http.post<ILevelLanguage>(this.resourceUrl, levelLanguage, { observe: 'response' });
  }

  update(levelLanguage: ILevelLanguage): Observable<EntityResponseType> {
    return this.http.put<ILevelLanguage>(this.resourceUrl, levelLanguage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILevelLanguage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILevelLanguage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
