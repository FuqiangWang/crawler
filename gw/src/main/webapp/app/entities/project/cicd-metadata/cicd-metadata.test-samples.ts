import { CdType } from 'app/entities/enumerations/cd-type.model';

import { ICicdMetadata, NewCicdMetadata } from './cicd-metadata.model';

export const sampleWithRequiredData: ICicdMetadata = {
  id: 86790,
  repositoryType: 'copy',
  repositoryName: 'Concrete Samoa Legacy',
  ciName: 'program Vietnam blockchains',
  ciUrl: 'Frozen payment AI',
  buildPkg: 'Islands Viaduct parse',
  cdType: CdType['AUTO'],
  rentId: 'wireless set',
  updateTime: 'Avon',
  createTime: 'Hat Berkshire North',
};

export const sampleWithPartialData: ICicdMetadata = {
  id: 33712,
  repositoryType: 'models intelligence Ball',
  repositoryName: 'Dynamic',
  ciName: 'input deposit white',
  ciUrl: 'Producer',
  buildPkg: 'Corporate copying',
  mirrorRepository: 'Cotton',
  cdType: CdType['MANUAL'],
  autoUser: 'Avon',
  autoPwd: 'City Motorway',
  deploy: 'ROI',
  rentId: 'Costa morph',
  updateTime: 'Movies',
  createTime: 'Frozen communities hack',
};

export const sampleWithFullData: ICicdMetadata = {
  id: 10756,
  repositoryType: 'Dong',
  repositoryName: 'Soap Ergonomic',
  ciName: 'transitional',
  ciUrl: 'Corporate Knolls Armenia',
  buildPkg: 'success',
  mirrorRepository: 'France',
  cdType: CdType['AUTO'],
  autoIp: 'Vista multimedia',
  autoPort: 47360,
  autoUser: 'Regional',
  autoPwd: 'Franc visionary silver',
  autoKey: '../fake-data/blob/hipster.txt',
  deploy: 'Cambridgeshire quantifying Pound',
  rentId: 'parsing',
  updateTime: 'interfaces Kwacha',
  createTime: 'Exclusive New',
};

export const sampleWithNewData: NewCicdMetadata = {
  repositoryType: 'Soft',
  repositoryName: 'Kansas',
  ciName: 'GB solid',
  ciUrl: 'Bedfordshire copying',
  buildPkg: 'Garden Heights',
  cdType: CdType['AUTO'],
  rentId: 'bluetooth relationships moratorium',
  updateTime: 'Home',
  createTime: 'New',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
