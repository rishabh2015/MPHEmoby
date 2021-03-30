export interface ISector {
  id?: number;
  code?: string;
  libelle: string | '';
  disabled : boolean | false;
}

export class Sector implements ISector {
  constructor(public id?: number, public code?: string, public libelle= '', public disabled = false) {}
}
