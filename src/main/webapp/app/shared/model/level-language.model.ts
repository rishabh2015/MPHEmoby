export interface ILevelLanguage {
  id?: number;
  code?: string;
  libelle?: string;
}

export class LevelLanguage implements ILevelLanguage {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
