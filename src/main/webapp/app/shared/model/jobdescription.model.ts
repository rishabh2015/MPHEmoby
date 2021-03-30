import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';
import { IEducationlevel } from 'app/shared/model/educationlevel.model';
import { IProjectphaseActivity } from 'app/shared/model/projectphase-activity.model';
import { ITechnicalDiscipline } from 'app/shared/model/technical-discipline.model';
import { IExperience } from 'app/shared/model/experience.model';
import { ILanguage } from 'app/shared/model/language.model';
import { ISector } from 'app/shared/model/sector.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { IContent } from './content.model';

export interface IJobdescription {
  id?: number;
  name?: string;
  creation_dt?: Moment;
  gender?: Gender;
  filename?: string;
  projectId?: number;
  nationalities?: ICountry[];
  locations?: ICountry[];
  educationlevels?: IEducationlevel[];
  projectphaseActivities?: IProjectphaseActivity[];
  technicalDisciplines?: ITechnicalDiscipline[];
  experiences?: IExperience[];
  languages?: ILanguage[];
  sectors?: ISector[];
  content?: IContent;
  shortListedCount?: number;
  placedCount?: number;
  contentId?: number;
}

export class Jobdescription implements IJobdescription {
  constructor(
    public id?: number,
    public name?: string,
    public creation_dt?: Moment,
    public gender?: Gender,
    public filename?: string,
    public projectId?: number,
    public nationalities?: ICountry[],
    public locations?: ICountry[],
    public educationlevels?: IEducationlevel[],
    public projectphaseActivities?: IProjectphaseActivity[],
    public technicalDisciplines?: ITechnicalDiscipline[],
    public experiences?: IExperience[],
    public languages?: ILanguage[],
    public sectors?: ISector[],
    public content?: IContent,
    public shortListedCount?: number,
    public placedCount?: number,
    public contentId?: number,
  ) {}
}
