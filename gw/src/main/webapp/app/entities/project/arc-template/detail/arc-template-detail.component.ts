import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArcTemplate } from '../arc-template.model';

@Component({
  selector: 'jhi-arc-template-detail',
  templateUrl: './arc-template-detail.component.html',
})
export class ArcTemplateDetailComponent implements OnInit {
  arcTemplate: IArcTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arcTemplate }) => {
      this.arcTemplate = arcTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
