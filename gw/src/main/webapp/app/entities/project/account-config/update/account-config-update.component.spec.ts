import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccountConfigFormService } from './account-config-form.service';
import { AccountConfigService } from '../service/account-config.service';
import { IAccountConfig } from '../account-config.model';

import { AccountConfigUpdateComponent } from './account-config-update.component';

describe('AccountConfig Management Update Component', () => {
  let comp: AccountConfigUpdateComponent;
  let fixture: ComponentFixture<AccountConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accountConfigFormService: AccountConfigFormService;
  let accountConfigService: AccountConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccountConfigUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AccountConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accountConfigFormService = TestBed.inject(AccountConfigFormService);
    accountConfigService = TestBed.inject(AccountConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const accountConfig: IAccountConfig = { id: 456 };

      activatedRoute.data = of({ accountConfig });
      comp.ngOnInit();

      expect(comp.accountConfig).toEqual(accountConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountConfig>>();
      const accountConfig = { id: 123 };
      jest.spyOn(accountConfigFormService, 'getAccountConfig').mockReturnValue(accountConfig);
      jest.spyOn(accountConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountConfig }));
      saveSubject.complete();

      // THEN
      expect(accountConfigFormService.getAccountConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accountConfigService.update).toHaveBeenCalledWith(expect.objectContaining(accountConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountConfig>>();
      const accountConfig = { id: 123 };
      jest.spyOn(accountConfigFormService, 'getAccountConfig').mockReturnValue({ id: null });
      jest.spyOn(accountConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountConfig }));
      saveSubject.complete();

      // THEN
      expect(accountConfigFormService.getAccountConfig).toHaveBeenCalled();
      expect(accountConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountConfig>>();
      const accountConfig = { id: 123 };
      jest.spyOn(accountConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accountConfigService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
