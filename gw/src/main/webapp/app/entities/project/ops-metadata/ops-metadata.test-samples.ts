import { IOpsMetadata, NewOpsMetadata } from './ops-metadata.model';

export const sampleWithRequiredData: IOpsMetadata = {
  id: 86101,
  opsSystem: 'cultivate HDD Tactics',
  createTime: 'web-enabled Wooden',
  updateTime: 'Refined Money',
};

export const sampleWithPartialData: IOpsMetadata = {
  id: 79003,
  opsSystem: 'calculate',
  rentId: 'Garden',
  createTime: 'schemas Facilitator Dinar',
  updateTime: 'Plastic Sports',
};

export const sampleWithFullData: IOpsMetadata = {
  id: 19676,
  opsSystem: 'Kentucky streamline fuchsia',
  rentId: 'envisioneer Technician data-warehouse',
  createTime: 'blue',
  updateTime: 'override indexing Automotive',
};

export const sampleWithNewData: NewOpsMetadata = {
  opsSystem: 'Granite Concrete Maldives',
  createTime: 'Bedfordshire',
  updateTime: 'interface functionalities',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
