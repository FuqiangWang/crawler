import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOpsMetadata } from '../ops-metadata.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ops-metadata.test-samples';

import { OpsMetadataService } from './ops-metadata.service';

const requireRestSample: IOpsMetadata = {
  ...sampleWithRequiredData,
};

describe('OpsMetadata Service', () => {
  let service: OpsMetadataService;
  let httpMock: HttpTestingController;
  let expectedResult: IOpsMetadata | IOpsMetadata[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OpsMetadataService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a OpsMetadata', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const opsMetadata = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(opsMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OpsMetadata', () => {
      const opsMetadata = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(opsMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OpsMetadata', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OpsMetadata', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OpsMetadata', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOpsMetadataToCollectionIfMissing', () => {
      it('should add a OpsMetadata to an empty array', () => {
        const opsMetadata: IOpsMetadata = sampleWithRequiredData;
        expectedResult = service.addOpsMetadataToCollectionIfMissing([], opsMetadata);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opsMetadata);
      });

      it('should not add a OpsMetadata to an array that contains it', () => {
        const opsMetadata: IOpsMetadata = sampleWithRequiredData;
        const opsMetadataCollection: IOpsMetadata[] = [
          {
            ...opsMetadata,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOpsMetadataToCollectionIfMissing(opsMetadataCollection, opsMetadata);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OpsMetadata to an array that doesn't contain it", () => {
        const opsMetadata: IOpsMetadata = sampleWithRequiredData;
        const opsMetadataCollection: IOpsMetadata[] = [sampleWithPartialData];
        expectedResult = service.addOpsMetadataToCollectionIfMissing(opsMetadataCollection, opsMetadata);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opsMetadata);
      });

      it('should add only unique OpsMetadata to an array', () => {
        const opsMetadataArray: IOpsMetadata[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const opsMetadataCollection: IOpsMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addOpsMetadataToCollectionIfMissing(opsMetadataCollection, ...opsMetadataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const opsMetadata: IOpsMetadata = sampleWithRequiredData;
        const opsMetadata2: IOpsMetadata = sampleWithPartialData;
        expectedResult = service.addOpsMetadataToCollectionIfMissing([], opsMetadata, opsMetadata2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opsMetadata);
        expect(expectedResult).toContain(opsMetadata2);
      });

      it('should accept null and undefined values', () => {
        const opsMetadata: IOpsMetadata = sampleWithRequiredData;
        expectedResult = service.addOpsMetadataToCollectionIfMissing([], null, opsMetadata, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opsMetadata);
      });

      it('should return initial array if no OpsMetadata is added', () => {
        const opsMetadataCollection: IOpsMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addOpsMetadataToCollectionIfMissing(opsMetadataCollection, undefined, null);
        expect(expectedResult).toEqual(opsMetadataCollection);
      });
    });

    describe('compareOpsMetadata', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOpsMetadata(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOpsMetadata(entity1, entity2);
        const compareResult2 = service.compareOpsMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOpsMetadata(entity1, entity2);
        const compareResult2 = service.compareOpsMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOpsMetadata(entity1, entity2);
        const compareResult2 = service.compareOpsMetadata(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
