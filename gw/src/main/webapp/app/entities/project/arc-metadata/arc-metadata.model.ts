import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { IArcTemplate } from 'app/entities/project/arc-template/arc-template.model';
import { ArcModelType } from 'app/entities/enumerations/arc-model-type.model';

export interface IArcMetadata {
  id: number;
  type?: ArcModelType | null;
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
  projectMetadata?: Pick<IProjectMetadata, 'id'> | null;
  arcTemplate?: Pick<IArcTemplate, 'id' | 'name'> | null;
}

export type NewArcMetadata = Omit<IArcMetadata, 'id'> & { id: null };
