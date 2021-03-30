export interface ISectorsubsector {
  id?: number;
  sectorId?: number;
  sectorLibelle?: string;
  subsectorId?: number;
  subsectorLibelle?: string;
}

export class Sectorsubsector implements ISectorsubsector {
  constructor(public id?: number, public sectorId?: number, public sectorLibelle?: string, public subsectorId?: number, public subsectorLibelle?: string) {}
}
