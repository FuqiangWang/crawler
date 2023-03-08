import { IAccountConfig, NewAccountConfig } from './account-config.model';

export const sampleWithRequiredData: IAccountConfig = {
  id: 76042,
  rendId: 'system Gabon Planner',
};

export const sampleWithPartialData: IAccountConfig = {
  id: 91031,
  gitHubPwd: 'Forint',
  gitLabPwd: 'firewall Corners Vermont',
  giteePwd: 'Iraq primary Macao',
  rendId: 'USB',
};

export const sampleWithFullData: IAccountConfig = {
  id: 84268,
  gitHubUser: 'Cheese 1080p',
  gitHubPwd: 'Marketing',
  gitLabUser: 'calculate XSS override',
  gitLabPwd: 'Roads Division',
  giteeUser: 'Refined',
  giteePwd: 'Personal',
  dockerHubUser: 'Won',
  dockHubPwd: 'teal Executive',
  rendId: 'matrix aggregate Steel',
};

export const sampleWithNewData: NewAccountConfig = {
  rendId: 'Unit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
