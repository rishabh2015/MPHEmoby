import { IJobdescription } from 'app/shared/model/jobdescription.model';

export interface IProject {
  id?: number;
  code?: string;
  libelle?: string;
  jhi_userId?: number;
  jobdescriptions?: IJobdescription[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public jhi_userId?: number,
    public jobdescriptions?: IJobdescription[]
  ) {}
}
