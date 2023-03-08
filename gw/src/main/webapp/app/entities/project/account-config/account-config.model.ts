export interface IAccountConfig {
  id: number;
  gitHubUser?: string | null;
  gitHubPwd?: string | null;
  gitLabUser?: string | null;
  gitLabPwd?: string | null;
  giteeUser?: string | null;
  giteePwd?: string | null;
  dockerHubUser?: string | null;
  dockHubPwd?: string | null;
  rendId?: string | null;
}

export type NewAccountConfig = Omit<IAccountConfig, 'id'> & { id: null };
