import { ProjectType } from 'app/entities/enumerations/project-type.model';

export interface IProjectMetadata {
  id: number;
  name?: string | null;
  type?: ProjectType | null;
  language?: string | null;
  langVersion?: string | null;
  buildTool?: string | null;
  buildToolVersion?: string | null;
  banner?: string | null;
  favicon?: string | null;
  version?: string | null;
  rentId?: string | null;
  updateTime?: string | null;
  createTime?: string | null;
}

export type NewProjectMetadata = Omit<IProjectMetadata, 'id'> & { id: null };
