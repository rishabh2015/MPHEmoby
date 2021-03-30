import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { ISearchFilter } from 'app/shared/model/ISearchFilter.model';
import { Jobopening } from 'app/shared/model/jobopening.model';
import { Observable } from 'rxjs';

type EntityResponseType = HttpResponse<ISearchFilter>;
@Injectable({ providedIn: 'root' })
export class CandidateSearchService {
  public resourceUrl = SERVER_API_URL + 'api/jobopening';
  public searchCandidatesUrl = 'api/searchCandidates';

  constructor(protected http: HttpClient) {}
  public jobopening(title: string): Observable<Jobopening[]> {
    return this.http.get<Jobopening[]>(this.resourceUrl, { params: { title } });
  }

  searchCandidates(search: ISearchFilter): Observable<EntityResponseType> {
    return this.http.post<ISearchFilter>(this.searchCandidatesUrl, search, { observe: 'response' });
  }
}
