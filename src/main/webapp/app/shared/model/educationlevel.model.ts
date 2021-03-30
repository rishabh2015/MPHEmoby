export interface IEducationlevel {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Educationlevel implements IEducationlevel {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
