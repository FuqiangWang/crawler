import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ops-metadata',
        data: { pageTitle: 'gwApp.projectOpsMetadata.home.title' },
        loadChildren: () => import('./project/ops-metadata/ops-metadata.module').then(m => m.ProjectOpsMetadataModule),
      },
      {
        path: 'project-metadata',
        data: { pageTitle: 'gwApp.projectProjectMetadata.home.title' },
        loadChildren: () => import('./project/project-metadata/project-metadata.module').then(m => m.ProjectProjectMetadataModule),
      },
      {
        path: 'cicd-metadata',
        data: { pageTitle: 'gwApp.projectCicdMetadata.home.title' },
        loadChildren: () => import('./project/cicd-metadata/cicd-metadata.module').then(m => m.ProjectCicdMetadataModule),
      },
      {
        path: 'arc-metadata',
        data: { pageTitle: 'gwApp.projectArcMetadata.home.title' },
        loadChildren: () => import('./project/arc-metadata/arc-metadata.module').then(m => m.ProjectArcMetadataModule),
      },
      {
        path: 'arc-template',
        data: { pageTitle: 'gwApp.projectArcTemplate.home.title' },
        loadChildren: () => import('./project/arc-template/arc-template.module').then(m => m.ProjectArcTemplateModule),
      },
      {
        path: 'account-config',
        data: { pageTitle: 'gwApp.projectAccountConfig.home.title' },
        loadChildren: () => import('./project/account-config/account-config.module').then(m => m.ProjectAccountConfigModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
