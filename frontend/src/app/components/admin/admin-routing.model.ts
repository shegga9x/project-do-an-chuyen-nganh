import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//component
import { HomeComponent } from './pages/home/home.component';
import { AdminComponent } from './admin.component';

const routes: Routes = [{
    path: '', component: AdminComponent,
    children: [
        {
            path: '', redirectTo: 'home', pathMatch: 'full'
        },
        {
            path: 'home', component: HomeComponent
        }
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AdminRoutingModule { }