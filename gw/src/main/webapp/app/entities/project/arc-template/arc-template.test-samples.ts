import { IArcTemplate, NewArcTemplate } from './arc-template.model';

export const sampleWithRequiredData: IArcTemplate = {
  id: 90542,
  name: 'Practical',
  packageName: 'Mission',
  serverPort: 23192,
  rentId: 'Hawaii',
  updateTime: 'Alaska withdrawal',
  createTime: 'executive',
};

export const sampleWithPartialData: IArcTemplate = {
  id: 92743,
  name: 'Virginia',
  packageName: 'even-keeled full-range Internal',
  serviceDiscoveryType: 'protocol',
  cacheProvider: 'Product Industrial enhance',
  serverPort: 36703,
  languages: 'solid blue',
  rentId: 'back-end Concrete International',
  updateTime: 'Hawaii',
  createTime: 'monitor utilisation',
};

export const sampleWithFullData: IArcTemplate = {
  id: 39925,
  name: 'payment',
  packageName: 'background Table',
  serviceDiscoveryType: 'enable Customer-focused',
  devDatabaseType: 'Fish Legacy',
  prodDatabaseType: 'exploit',
  cacheProvider: 'Books Incredible deposit',
  messageBroker: 'AI back-end',
  serverPort: 53229,
  frontMessage: 'synthesizing Supervisor Corporate',
  clientFramework: 'invoice matrix directional',
  languages: 'Computer',
  testFramework: 'Savings Cambridgeshire',
  rentId: 'Quality',
  updateTime: 'transmitting',
  createTime: 'Highway invoice',
};

export const sampleWithNewData: NewArcTemplate = {
  name: 'Avon Leu',
  packageName: 'SAS Coordinator',
  serverPort: 32900,
  rentId: 'Communications Nevada',
  updateTime: 'District Handmade Avon',
  createTime: 'Persevering Right-sized of',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
