export interface IProjectphase {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Projectphase implements IProjectphase {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
