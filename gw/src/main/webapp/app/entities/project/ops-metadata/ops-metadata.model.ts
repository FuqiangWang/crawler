import { IProjectMetadata } from 'app/entities/project/project-metadata/project-metadata.model';

export interface IOpsMetadata {
  id: number;
  opsSystem?: string | null;
  rentId?: string | null;
  createTime?: string | null;
  updateTime?: string | null;
  projectMetadata?: Pick<IProjectMetadata, 'id'> | null;
}

export type NewOpsMetadata = Omit<IOpsMetadata, 'id'> & { id: null };
