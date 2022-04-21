import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//component
import { PageNotFoundComponent } from './components/user/pages/page-not-found/page-not-found.component';

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
