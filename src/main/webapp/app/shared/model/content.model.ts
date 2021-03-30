export interface IContent {
  id?: number;
  dataContentType?: string;
  data?: any;
  text?: any;
  filename?: any;
}

export class Content implements IContent {
  constructor(public id?: number, public dataContentType?: string, public data?: any, public text?: any, public filename?: any) {}
}
