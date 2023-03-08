import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICicdMetadata } from '../cicd-metadata.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cicd-metadata.test-samples';

import { CicdMetadataService } from './cicd-metadata.service';

const requireRestSample: ICicdMetadata = {
  ...sampleWithRequiredData,
};

describe('CicdMetadata Service', () => {
  let service: CicdMetadataService;
  let httpMock: HttpTestingController;
  let expectedResult: ICicdMetadata | ICicdMetadata[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CicdMetadataService);
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

    it('should create a CicdMetadata', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cicdMetadata = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cicdMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CicdMetadata', () => {
      const cicdMetadata = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cicdMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CicdMetadata', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CicdMetadata', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CicdMetadata', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCicdMetadataToCollectionIfMissing', () => {
      it('should add a CicdMetadata to an empty array', () => {
        const cicdMetadata: ICicdMetadata = sampleWithRequiredData;
        expectedResult = service.addCicdMetadataToCollectionIfMissing([], cicdMetadata);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cicdMetadata);
      });

      it('should not add a CicdMetadata to an array that contains it', () => {
        const cicdMetadata: ICicdMetadata = sampleWithRequiredData;
        const cicdMetadataCollection: ICicdMetadata[] = [
          {
            ...cicdMetadata,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCicdMetadataToCollectionIfMissing(cicdMetadataCollection, cicdMetadata);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CicdMetadata to an array that doesn't contain it", () => {
        const cicdMetadata: ICicdMetadata = sampleWithRequiredData;
        const cicdMetadataCollection: ICicdMetadata[] = [sampleWithPartialData];
        expectedResult = service.addCicdMetadataToCollectionIfMissing(cicdMetadataCollection, cicdMetadata);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cicdMetadata);
      });

      it('should add only unique CicdMetadata to an array', () => {
        const cicdMetadataArray: ICicdMetadata[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cicdMetadataCollection: ICicdMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addCicdMetadataToCollectionIfMissing(cicdMetadataCollection, ...cicdMetadataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cicdMetadata: ICicdMetadata = sampleWithRequiredData;
        const cicdMetadata2: ICicdMetadata = sampleWithPartialData;
        expectedResult = service.addCicdMetadataToCollectionIfMissing([], cicdMetadata, cicdMetadata2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cicdMetadata);
        expect(expectedResult).toContain(cicdMetadata2);
      });

      it('should accept null and undefined values', () => {
        const cicdMetadata: ICicdMetadata = sampleWithRequiredData;
        expectedResult = service.addCicdMetadataToCollectionIfMissing([], null, cicdMetadata, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cicdMetadata);
      });

      it('should return initial array if no CicdMetadata is added', () => {
        const cicdMetadataCollection: ICicdMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addCicdMetadataToCollectionIfMissing(cicdMetadataCollection, undefined, null);
        expect(expectedResult).toEqual(cicdMetadataCollection);
      });
    });

    describe('compareCicdMetadata', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCicdMetadata(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCicdMetadata(entity1, entity2);
        const compareResult2 = service.compareCicdMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCicdMetadata(entity1, entity2);
        const compareResult2 = service.compareCicdMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCicdMetadata(entity1, entity2);
        const compareResult2 = service.compareCicdMetadata(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
