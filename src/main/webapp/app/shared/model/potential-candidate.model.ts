import { Moment } from 'moment';
import { ICandidate } from './candidate.model';

export interface IPotentialCandidate {
  id?: number;
  matching_percent?: number;
  creation_dt?: Moment;
  jobdescriptionId?: number;
  candidateId?: number;
}

export class PotentialCandidate implements IPotentialCandidate {
  constructor(
    public id?: number,
    public matching_percent?: number,
    public creation_dt?: Moment,
    public jobdescriptionId?: number,
    public candidate?: ICandidate
  ) {}
}
