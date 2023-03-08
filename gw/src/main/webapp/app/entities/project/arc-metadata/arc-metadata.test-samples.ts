import { ArcModelType } from 'app/entities/enumerations/arc-model-type.model';

import { IArcMetadata, NewArcMetadata } from './arc-metadata.model';

export const sampleWithRequiredData: IArcMetadata = {
  id: 35311,
  type: ArcModelType['TEMPLATE'],
  packageName: 'Salad Total',
  serverPort: 27600,
  rentId: 'Movies Zealand',
  updateTime: 'Borders quantifying Customizable',
  createTime: 'Maine',
};

export const sampleWithPartialData: IArcMetadata = {
  id: 9049,
  type: ArcModelType['MANUAL'],
  packageName: 'Rial',
  devDatabaseType: 'Chips Timor-Leste Soft',
  prodDatabaseType: 'Cambridgeshire model',
  cacheProvider: 'copying mindshare',
  messageBroker: 'tan Buckinghamshire strategic',
  serverPort: 87482,
  clientFramework: 'Gloves deposit Shoes',
  languages: 'Investment Buckinghamshire Pizza',
  testFramework: 'navigating streamline value-added',
  rentId: 'Peso',
  updateTime: 'Toys overriding methodologies',
  createTime: 'Pizza Gloves',
};

export const sampleWithFullData: IArcMetadata = {
  id: 49672,
  type: ArcModelType['TEMPLATE'],
  packageName: 'Fork',
  serviceDiscoveryType: 'Fresh Afghani',
  devDatabaseType: 'Planner Ergonomic',
  prodDatabaseType: 'wireless protocol',
  cacheProvider: 'Refined responsive Shoes',
  messageBroker: 'initiatives intuitive synthesize',
  serverPort: 70875,
  frontMessage: 'Chicken',
  clientFramework: 'Cambridgeshire Automotive Chips',
  languages: 'National',
  testFramework: 'Mexico Canadian AI',
  rentId: 'composite Cotton',
  updateTime: 'visualize',
  createTime: 'Profit-focused leverage intangible',
};

export const sampleWithNewData: NewArcMetadata = {
  type: ArcModelType['MANUAL'],
  packageName: 'generating AI enable',
  serverPort: 25221,
  rentId: 'Buckinghamshire Wells B2C',
  updateTime: 'Georgia',
  createTime: 'Sleek',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
