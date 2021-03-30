export interface ICountry {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Country implements ICountry {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
