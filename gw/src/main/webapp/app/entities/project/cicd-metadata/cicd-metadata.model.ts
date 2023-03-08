import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';
import { CdType } from 'app/entities/enumerations/cd-type.model';

export interface ICicdMetadata {
  id: number;
  repositoryType?: string | null;
  repositoryName?: string | null;
  ciName?: string | null;
  ciUrl?: string | null;
  buildPkg?: string | null;
  mirrorRepository?: string | null;
  cdType?: CdType | null;
  autoIp?: string | null;
  autoPort?: number | null;
  autoUser?: string | null;
  autoPwd?: string | null;
  autoKey?: string | null;
  deploy?: string | null;
  rentId?: string | null;
  updateTime?: string | null;
  createTime?: string | null;
  projectMetadata?: Pick<IProjectMetadata, 'id'> | null;
}

export type NewCicdMetadata = Omit<ICicdMetadata, 'id'> & { id: null };
