import { Moment } from 'moment';

export interface IJobOpening {
  id?: number;
  title?: string;
  guid?: string;
  text_clean?: any;
  fileContentType?: string;
  file?: any;
  creation_date?: Moment;
  delete_date?: Moment;
  jobdescription_text?: any;
}

export class JobOpening implements IJobOpening {
  constructor(
    public id?: number,
    public title?: string,
    public guid?: string,
    public text_clean?: any,
    public fileContentType?: string,
    public file?: any,
    public creation_date?: Moment,
    public delete_date?: Moment,
    public jobdescription_text?: any
  ) {}
}
