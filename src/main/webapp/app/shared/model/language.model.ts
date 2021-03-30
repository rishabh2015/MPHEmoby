export interface ILanguage {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Language implements ILanguage {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
