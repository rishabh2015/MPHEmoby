export interface IProjectphaseActivity {
  id?: number;
  projectphaseId?: number;
  projectphaseLibelle?: string;
  activityId?: number;
  activityLibelle?: string;
}

export class ProjectphaseActivity implements IProjectphaseActivity {
  constructor(public id?: number, public projectphaseId?: number, public projectphaseLibelle?: string, public activityId?: number, public activityLibelle?: string) {}
}
