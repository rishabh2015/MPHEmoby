export interface ITechnicalDiscipline {
  id?: number;
  code?: string;
  libelle?: string;
}

export class TechnicalDiscipline implements ITechnicalDiscipline {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
