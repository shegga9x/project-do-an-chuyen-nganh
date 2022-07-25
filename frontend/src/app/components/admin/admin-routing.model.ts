import { ManageEntitesComponent } from './pages/manage-entites/manage-entites.component';
import { CloseCourseRegistComponent } from './pages/close-course-regist/close-course-regist.component';
import { AutoImportGradeComponent } from './pages/auto-import-grade/auto-import-grade.component';
import { AutoImportAccountComponent } from './pages/auto-import-account/auto-import-account.component';
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
            path: 'home', component: ManageEntitesComponent
        },
        {
            path: 'auto-import-account', component: AutoImportAccountComponent
        },
        {
            path: 'auto-import-grade', component: AutoImportGradeComponent
        },
        {
            path: 'close-course-regist', component: CloseCourseRegistComponent
        },
        {
            path: 'manage-entites', component: ManageEntitesComponent
        }

    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AdminRoutingModule { }