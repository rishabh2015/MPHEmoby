import { ILanguage } from './language.model';
import { ILevelLanguage } from './level-language.model';

export interface ICandidateLevelLanguage {
  id?: number;
  levelLanguage?: ILevelLanguage;
  language?: ILanguage;
}

export class CandidateLevelLanguage implements ICandidateLevelLanguage {
  constructor(public id?: number, public levelLanguage?: ILevelLanguage, public language?: ILanguage) {}
}
