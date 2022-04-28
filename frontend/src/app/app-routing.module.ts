import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/user/pages/commons/page-not-found/page-not-found.component';
//component

const userModule = () => import('src/app/components/user/user.model').then(x => x.userModule)

const routes: Routes = [
  {
    path: '', redirectTo: 'user', pathMatch: 'full'
  },
  {
    path: 'oauth2/redirect', redirectTo: 'user', pathMatch: 'full'
  },
  {
    path: 'user', loadChildren:userModule
  },
  {
    path: '**', pathMatch: 'full',
    component: PageNotFoundComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
