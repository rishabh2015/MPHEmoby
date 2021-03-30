import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';
import { ISectorsubsector } from 'app/shared/model/sectorsubsector.model';
import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { IEducationlevel } from './educationlevel.model';
import { IExperience } from './experience.model';
import { ICandidateLevelLanguage } from './candidate-level-language.model';
import { IMobyStatus } from './moby-status.model';

export interface ICandidate {
  id?: number;
  gender?: Gender;
  last_name?: string;
  first_name?: string;
  email?: string;
  phone_number?: string;
  date_of_birth?: Moment;
  text_clean?: any;
  guid?: string;
  creation_date?: Moment;
  update_date?: Moment;
  comment?: any;
  nationality?: ICountry;
  dualNationalities?: ICountry[];
  location?: ICountry;
  educationlevel?: IEducationlevel;
  experience?: IExperience;
  mobyStatus?: IMobyStatus;
  sectorSubsectors?: ISectorsubsector[];
  projectphaseActivities?: IProjectphaseActivity[];
  technicalDisciplines?: ITechnicalDiscipline[];
  shortlistedId?: number;
  placedId?: number;
  levelLanguages?: ICandidateLevelLanguage[];
}

export class Candidate implements ICandidate {
  constructor(
    public id?: number,
    public gender?: Gender,
    public last_name?: string,
    public first_name?: string,
    public email?: string,
    public phone_number?: string,
    public date_of_birth?: Moment,
    public text_clean?: any,
    public guid?: string,
    public creation_date?: Moment,
    public update_date?: Moment,
    public comment?: any,
    public nationality?: ICountry,
    public dualNationalities?: ICountry[],
    public location?: ICountry,
    public educationlevel?: IEducationlevel,
    public experience?: IExperience,
    public mobyStatus?: IMobyStatus,
    public sectorSubsectors?: ISectorsubsector[],
    public projectphaseActivities?: IProjectphaseActivity[],
    public technicalDisciplines?: ITechnicalDiscipline[],
    public shortlistedId?: number,
    public placedId?: number,
    public levelLanguages?: ICandidateLevelLanguage[]
  ) {}
}
