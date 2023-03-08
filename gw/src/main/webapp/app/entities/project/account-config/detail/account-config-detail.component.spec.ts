import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountConfigDetailComponent } from './account-config-detail.component';

describe('AccountConfig Management Detail Component', () => {
  let comp: AccountConfigDetailComponent;
  let fixture: ComponentFixture<AccountConfigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccountConfigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accountConfig: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccountConfigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccountConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accountConfig on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accountConfig).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
