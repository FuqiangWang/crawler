import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArcTemplate } from '../arc-template.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../arc-template.test-samples';

import { ArcTemplateService } from './arc-template.service';

const requireRestSample: IArcTemplate = {
  ...sampleWithRequiredData,
};

describe('ArcTemplate Service', () => {
  let service: ArcTemplateService;
  let httpMock: HttpTestingController;
  let expectedResult: IArcTemplate | IArcTemplate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArcTemplateService);
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

    it('should create a ArcTemplate', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const arcTemplate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(arcTemplate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ArcTemplate', () => {
      const arcTemplate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(arcTemplate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ArcTemplate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ArcTemplate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ArcTemplate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArcTemplateToCollectionIfMissing', () => {
      it('should add a ArcTemplate to an empty array', () => {
        const arcTemplate: IArcTemplate = sampleWithRequiredData;
        expectedResult = service.addArcTemplateToCollectionIfMissing([], arcTemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arcTemplate);
      });

      it('should not add a ArcTemplate to an array that contains it', () => {
        const arcTemplate: IArcTemplate = sampleWithRequiredData;
        const arcTemplateCollection: IArcTemplate[] = [
          {
            ...arcTemplate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArcTemplateToCollectionIfMissing(arcTemplateCollection, arcTemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ArcTemplate to an array that doesn't contain it", () => {
        const arcTemplate: IArcTemplate = sampleWithRequiredData;
        const arcTemplateCollection: IArcTemplate[] = [sampleWithPartialData];
        expectedResult = service.addArcTemplateToCollectionIfMissing(arcTemplateCollection, arcTemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arcTemplate);
      });

      it('should add only unique ArcTemplate to an array', () => {
        const arcTemplateArray: IArcTemplate[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const arcTemplateCollection: IArcTemplate[] = [sampleWithRequiredData];
        expectedResult = service.addArcTemplateToCollectionIfMissing(arcTemplateCollection, ...arcTemplateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const arcTemplate: IArcTemplate = sampleWithRequiredData;
        const arcTemplate2: IArcTemplate = sampleWithPartialData;
        expectedResult = service.addArcTemplateToCollectionIfMissing([], arcTemplate, arcTemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arcTemplate);
        expect(expectedResult).toContain(arcTemplate2);
      });

      it('should accept null and undefined values', () => {
        const arcTemplate: IArcTemplate = sampleWithRequiredData;
        expectedResult = service.addArcTemplateToCollectionIfMissing([], null, arcTemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arcTemplate);
      });

      it('should return initial array if no ArcTemplate is added', () => {
        const arcTemplateCollection: IArcTemplate[] = [sampleWithRequiredData];
        expectedResult = service.addArcTemplateToCollectionIfMissing(arcTemplateCollection, undefined, null);
        expect(expectedResult).toEqual(arcTemplateCollection);
      });
    });

    describe('compareArcTemplate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArcTemplate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArcTemplate(entity1, entity2);
        const compareResult2 = service.compareArcTemplate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArcTemplate(entity1, entity2);
        const compareResult2 = service.compareArcTemplate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArcTemplate(entity1, entity2);
        const compareResult2 = service.compareArcTemplate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
