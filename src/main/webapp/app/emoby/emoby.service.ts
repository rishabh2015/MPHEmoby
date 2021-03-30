import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from '../app.constants';
import { Injectable } from '@angular/core';
import { ISector } from 'app/shared/model/sector.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmobyService {

  constructor(
    private http: HttpClient,
  ) {}


  callEmoby(jobdescriptionId:number, sectors?:ISector[]):Observable<HttpResponse<number>> {
    return this.http.post<number>(SERVER_API_URL + 'api/launchEmobyMatching', { jobdescriptionId, sectors }, { observe: 'response' });

  }
}
 