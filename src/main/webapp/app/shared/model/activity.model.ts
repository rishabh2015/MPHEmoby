export interface IActivity {
  id?: number;
  code?: string;
  libelle?: string;
}

export class Activity implements IActivity {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
