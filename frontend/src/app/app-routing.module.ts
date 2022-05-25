import { AccountService } from 'src/app/services';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/user/pages/commons/page-not-found/page-not-found.component';
import { AuthenticationGuard,Permissions } from '././services/auth-guard.service';

//component

const userModule = () => import('src/app/components/user/user.model').then(x => x.userModule)

const adminModule = () => import('src/app/components/admin/admin.model').then(x => x.adminModule)

const routes: Routes = [
  {
    path: '', redirectTo: 'user', pathMatch: 'full'
  },
  {
    path: 'user',  loadChildren: userModule
  },
  // {
  //   path: 'admin', canActivate: [AuthenticationGuard],loadChildren: adminModule
  // },
  {
    path: 'admin',loadChildren: adminModule
  },
  {
    path: '**', pathMatch: 'full',
    component: PageNotFoundComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthenticationGuard, AccountService, Permissions]
})
export class AppRoutingModule { }
