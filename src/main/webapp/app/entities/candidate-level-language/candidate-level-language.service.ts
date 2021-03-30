import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICandidateLevelLanguage } from 'app/shared/model/candidate-level-language.model';

type EntityResponseType = HttpResponse<ICandidateLevelLanguage>;
type EntityArrayResponseType = HttpResponse<ICandidateLevelLanguage[]>;

@Injectable({ providedIn: 'root' })
export class CandidateLevelLanguageService {
  public resourceUrl = SERVER_API_URL + 'api/candidate-level-languages';

  constructor(protected http: HttpClient) {}

  create(candidateLevelLanguage: ICandidateLevelLanguage): Observable<EntityResponseType> {
    return this.http.post<ICandidateLevelLanguage>(this.resourceUrl, candidateLevelLanguage, { observe: 'response' });
  }

  update(candidateLevelLanguage: ICandidateLevelLanguage): Observable<EntityResponseType> {
    return this.http.put<ICandidateLevelLanguage>(this.resourceUrl, candidateLevelLanguage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICandidateLevelLanguage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidateLevelLanguage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
