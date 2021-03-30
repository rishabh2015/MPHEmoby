import { ISector } from './sector.model';
import { IExperience } from './experience.model';
import { Country } from './country.model';
export interface ISearchFilter {
  experiencesId?: IExperience[];
  firstName?: string;
  jobOpeningId?: number;
  keywords?: string;
  sectorsId?: ISector[];
  lastName?: string;
  nationalitiesId?: Country[];
}
export class SearchFilter implements ISearchFilter {
  constructor(
    public experiencesId?: IExperience[],
    public firstName?: string,
    public jobOpeningId?: number,
    public keywords?: string,
    public sectorsId?: ISector[],
    public lastName?: string,
    public nationalitiesId?: Country[]
  ) {}
}
