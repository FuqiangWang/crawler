import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArcMetadata } from '../arc-metadata.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../arc-metadata.test-samples';

import { ArcMetadataService } from './arc-metadata.service';

const requireRestSample: IArcMetadata = {
  ...sampleWithRequiredData,
};

describe('ArcMetadata Service', () => {
  let service: ArcMetadataService;
  let httpMock: HttpTestingController;
  let expectedResult: IArcMetadata | IArcMetadata[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArcMetadataService);
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

    it('should create a ArcMetadata', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const arcMetadata = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(arcMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ArcMetadata', () => {
      const arcMetadata = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(arcMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ArcMetadata', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ArcMetadata', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ArcMetadata', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArcMetadataToCollectionIfMissing', () => {
      it('should add a ArcMetadata to an empty array', () => {
        const arcMetadata: IArcMetadata = sampleWithRequiredData;
        expectedResult = service.addArcMetadataToCollectionIfMissing([], arcMetadata);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arcMetadata);
      });

      it('should not add a ArcMetadata to an array that contains it', () => {
        const arcMetadata: IArcMetadata = sampleWithRequiredData;
        const arcMetadataCollection: IArcMetadata[] = [
          {
            ...arcMetadata,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArcMetadataToCollectionIfMissing(arcMetadataCollection, arcMetadata);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ArcMetadata to an array that doesn't contain it", () => {
        const arcMetadata: IArcMetadata = sampleWithRequiredData;
        const arcMetadataCollection: IArcMetadata[] = [sampleWithPartialData];
        expectedResult = service.addArcMetadataToCollectionIfMissing(arcMetadataCollection, arcMetadata);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arcMetadata);
      });

      it('should add only unique ArcMetadata to an array', () => {
        const arcMetadataArray: IArcMetadata[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const arcMetadataCollection: IArcMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addArcMetadataToCollectionIfMissing(arcMetadataCollection, ...arcMetadataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const arcMetadata: IArcMetadata = sampleWithRequiredData;
        const arcMetadata2: IArcMetadata = sampleWithPartialData;
        expectedResult = service.addArcMetadataToCollectionIfMissing([], arcMetadata, arcMetadata2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arcMetadata);
        expect(expectedResult).toContain(arcMetadata2);
      });

      it('should accept null and undefined values', () => {
        const arcMetadata: IArcMetadata = sampleWithRequiredData;
        expectedResult = service.addArcMetadataToCollectionIfMissing([], null, arcMetadata, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arcMetadata);
      });

      it('should return initial array if no ArcMetadata is added', () => {
        const arcMetadataCollection: IArcMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addArcMetadataToCollectionIfMissing(arcMetadataCollection, undefined, null);
        expect(expectedResult).toEqual(arcMetadataCollection);
      });
    });

    describe('compareArcMetadata', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArcMetadata(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArcMetadata(entity1, entity2);
        const compareResult2 = service.compareArcMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArcMetadata(entity1, entity2);
        const compareResult2 = service.compareArcMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArcMetadata(entity1, entity2);
        const compareResult2 = service.compareArcMetadata(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
