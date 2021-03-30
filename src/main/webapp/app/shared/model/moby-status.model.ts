export interface IMobyStatus {
  id?: number;
  code?: string;
  libelle?: string;
}

export class MobyStatus implements IMobyStatus {
  constructor(public id?: number, public code?: string, public libelle?: string) {}
}
