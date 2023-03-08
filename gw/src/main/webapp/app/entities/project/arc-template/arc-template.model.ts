export interface IArcTemplate {
  id: number;
  name?: string | null;
  packageName?: string | null;
  serviceDiscoveryType?: string | null;
  devDatabaseType?: string | null;
  prodDatabaseType?: string | null;
  cacheProvider?: string | null;
  messageBroker?: string | null;
  serverPort?: number | null;
  frontMessage?: string | null;
  clientFramework?: string | null;
  languages?: string | null;
  testFramework?: string | null;
  rentId?: string | null;
  updateTime?: string | null;
  createTime?: string | null;
}

export type NewArcTemplate = Omit<IArcTemplate, 'id'> & { id: null };
