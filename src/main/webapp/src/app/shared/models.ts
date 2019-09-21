export enum OrderOutcome {
  ACTIVE = "ACTIVE", CLOSED = "CLOSED"
}

export interface Client {
  id: number;
  code: string;
  name: string;
}

export interface Country {
  code: string;
  label: string;
}

interface ArticleFamily {
  code: string;
  label: string;
}

export interface Article {
  code: string;
  label: string;
  family: ArticleFamily;
  price: number;
}

