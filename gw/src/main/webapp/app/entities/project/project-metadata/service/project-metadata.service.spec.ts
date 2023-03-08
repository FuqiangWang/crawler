import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectMetadata } from '../project-metadata.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../project-metadata.test-samples';

import { ProjectMetadataService } from './project-metadata.service';

const requireRestSample: IProjectMetadata = {
  ...sampleWithRequiredData,
};

describe('ProjectMetadata Service', () => {
  let service: ProjectMetadataService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjectMetadata | IProjectMetadata[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectMetadataService);
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

    it('should create a ProjectMetadata', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projectMetadata = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projectMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectMetadata', () => {
      const projectMetadata = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projectMetadata).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectMetadata', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectMetadata', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProjectMetadata', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectMetadataToCollectionIfMissing', () => {
      it('should add a ProjectMetadata to an empty array', () => {
        const projectMetadata: IProjectMetadata = sampleWithRequiredData;
        expectedResult = service.addProjectMetadataToCollectionIfMissing([], projectMetadata);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectMetadata);
      });

      it('should not add a ProjectMetadata to an array that contains it', () => {
        const projectMetadata: IProjectMetadata = sampleWithRequiredData;
        const projectMetadataCollection: IProjectMetadata[] = [
          {
            ...projectMetadata,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectMetadataToCollectionIfMissing(projectMetadataCollection, projectMetadata);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectMetadata to an array that doesn't contain it", () => {
        const projectMetadata: IProjectMetadata = sampleWithRequiredData;
        const projectMetadataCollection: IProjectMetadata[] = [sampleWithPartialData];
        expectedResult = service.addProjectMetadataToCollectionIfMissing(projectMetadataCollection, projectMetadata);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectMetadata);
      });

      it('should add only unique ProjectMetadata to an array', () => {
        const projectMetadataArray: IProjectMetadata[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectMetadataCollection: IProjectMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addProjectMetadataToCollectionIfMissing(projectMetadataCollection, ...projectMetadataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectMetadata: IProjectMetadata = sampleWithRequiredData;
        const projectMetadata2: IProjectMetadata = sampleWithPartialData;
        expectedResult = service.addProjectMetadataToCollectionIfMissing([], projectMetadata, projectMetadata2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectMetadata);
        expect(expectedResult).toContain(projectMetadata2);
      });

      it('should accept null and undefined values', () => {
        const projectMetadata: IProjectMetadata = sampleWithRequiredData;
        expectedResult = service.addProjectMetadataToCollectionIfMissing([], null, projectMetadata, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectMetadata);
      });

      it('should return initial array if no ProjectMetadata is added', () => {
        const projectMetadataCollection: IProjectMetadata[] = [sampleWithRequiredData];
        expectedResult = service.addProjectMetadataToCollectionIfMissing(projectMetadataCollection, undefined, null);
        expect(expectedResult).toEqual(projectMetadataCollection);
      });
    });

    describe('compareProjectMetadata', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjectMetadata(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjectMetadata(entity1, entity2);
        const compareResult2 = service.compareProjectMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjectMetadata(entity1, entity2);
        const compareResult2 = service.compareProjectMetadata(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjectMetadata(entity1, entity2);
        const compareResult2 = service.compareProjectMetadata(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
