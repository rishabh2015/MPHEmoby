export interface ISubsector {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Subsector implements ISubsector {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
