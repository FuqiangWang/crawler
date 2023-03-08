import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AccountConfigService } from '../service/account-config.service';

import { AccountConfigComponent } from './account-config.component';

describe('AccountConfig Management Component', () => {
  let comp: AccountConfigComponent;
  let fixture: ComponentFixture<AccountConfigComponent>;
  let service: AccountConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'account-config', component: AccountConfigComponent }]), HttpClientTestingModule],
      declarations: [AccountConfigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AccountConfigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountConfigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AccountConfigService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.accountConfigs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to accountConfigService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAccountConfigIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAccountConfigIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
