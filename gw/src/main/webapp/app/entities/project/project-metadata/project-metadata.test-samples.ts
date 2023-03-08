import { ProjectType } from 'app/entities/enumerations/project-type.model';

import { IProjectMetadata, NewProjectMetadata } from './project-metadata.model';

export const sampleWithRequiredData: IProjectMetadata = {
  id: 69132,
  name: 'Cambridgeshire Steel Indiana',
  type: ProjectType['MICROSERVICE'],
  language: 'SDD',
  banner: 'District fuchsia brand',
  favicon: '../fake-data/blob/hipster.txt',
  version: '24/365 impactful generation',
  rentId: 'Tuna quantifying Keyboard',
  updateTime: 'Rupiah Colorado Savings',
  createTime: 'Cheese',
};

export const sampleWithPartialData: IProjectMetadata = {
  id: 52023,
  name: 'quantifying Wooden hacking',
  type: ProjectType['MONOLITH'],
  language: 'AGP software',
  langVersion: 'matrix JSON payment',
  buildTool: 'Ergonomic maximized',
  banner: 'Keyboard',
  favicon: '../fake-data/blob/hipster.txt',
  version: 'Customizable Lights help-desk',
  rentId: 'Cedi Parkway',
  updateTime: 'iterate',
  createTime: 'primary',
};

export const sampleWithFullData: IProjectMetadata = {
  id: 121,
  name: 'Multi-lateral Estate service-desk',
  type: ProjectType['MONOLITH'],
  language: 'parse auxiliary',
  langVersion: 'Tunnel bandwidth-monitored',
  buildTool: 'implementation grey Assistant',
  buildToolVersion: 'Sausages',
  banner: 'Account',
  favicon: '../fake-data/blob/hipster.txt',
  version: 'archive Central Berkshire',
  rentId: 'Franc Operations transmitter',
  updateTime: 'content',
  createTime: 'lavender',
};

export const sampleWithNewData: NewProjectMetadata = {
  name: 'Cheese Street',
  type: ProjectType['MICROSERVICE'],
  language: 'GB',
  banner: 'Buckinghamshire calculating synthesize',
  favicon: '../fake-data/blob/hipster.txt',
  version: 'Fresh Optimized',
  rentId: 'rich Gorgeous',
  updateTime: 'Factors',
  createTime: 'Salad',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
