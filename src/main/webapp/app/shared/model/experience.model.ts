export interface IExperience {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Experience implements IExperience {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
