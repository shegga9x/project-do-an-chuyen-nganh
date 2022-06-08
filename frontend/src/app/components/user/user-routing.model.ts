import { AuthenticationGuard, Permissions } from './../../services/auth-guard.service';
import * as timeTableComponent from './pages/professor/time-table/time-table.component';
import * as reportGradeComponent from './pages/professor/report-grade/report-grade.component';
import * as reportDateExamComponent from './pages/professor/report-date-exam/report-date-exam.component';
import * as courseRegistComponent from './pages/professor/course-regist/course-regist.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { StudentComponent } from './pages/student/student.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//component
import { UserComponent } from 'src/app/components/user/user.component';
import { CourseRegistComponent } from './pages/student/course-regist/course-regist.component';
import { ReportDateExamComponent } from './pages/student/report-date-exam/report-date-exam.component';
import { ReportGradeComponent } from './pages/student/report-grade/report-grade.component';
import { ReportSchoolFeesComponent } from './pages/student/report-school-fees/report-school-fees.component';
import { TimeTableComponent } from './pages/student/time-table/time-table.component';
import { HomeComponent } from 'src/app/components/user/pages/commons/home/home.component';
import { AboutUsComponent } from './pages/commons/about/about-us/about-us.component';
import { AboutComponent } from './pages/commons/about/about.component';
import { FacultyMemberComponent } from './pages/commons/about/faculty-member/faculty-member.component';
import { MissionAndVisionComponent } from './pages/commons/about/mission-and-vision/mission-and-vision.component';
import { OurPrincipalComponent } from './pages/commons/about/our-principal/our-principal.component';
import { ContactComponent } from './pages/commons/contact/contact.component';
import { CourseProgramComponent } from './pages/student/course-program/course-program.component';
import { DssvComponent } from './pages/student/dssv/dssv.component';


const routes: Routes = [{
    path: '', component: UserComponent,
    children: [
        {
            path: '', redirectTo: 'home', pathMatch: 'full'
        },
        {
            path: 'home', component: HomeComponent,
        },
        {
            path: 'student', canActivate: [AuthenticationGuard], component: StudentComponent,
            children: [
                {
                    path: '', component: HomeComponent
                },
                {
                    path: 'course-regist', component: CourseRegistComponent
                },
                {
                    path: 'report-date-exam', component: ReportDateExamComponent
                },
                {
                    path: 'report-grade', component: ReportGradeComponent
                },
                {
                    path: 'report-school-fees', component: ReportSchoolFeesComponent
                },
                {
                    path: 'time-table', component: TimeTableComponent
                },
                {
                    path: 'course-program', component: CourseProgramComponent
                },
                {
                    path: 'dssv', component: DssvComponent
                }

            ]
        },
        {
            path: 'professor', canActivate: [AuthenticationGuard], component: ProfessorComponent,
            children: [
                {
                    path: '', component: HomeComponent
                },
                {
                    path: 'course-regist', component: courseRegistComponent.CourseRegistComponent
                },
                {
                    path: 'report-date-exam', component: reportDateExamComponent.ReportDateExamComponent
                },
                {
                    path: 'report-grade', component: reportGradeComponent.ReportGradeComponent
                },

                {
                    path: 'time-table', component: timeTableComponent.TimeTableComponent
                },
            ]
        },
        {
            path: 'about', component: AboutComponent,
            children: [
                {
                    path: '', redirectTo: 'about-us', pathMatch: 'full'
                },
                {
                    path: 'about-us', component: AboutUsComponent
                },
                {
                    path: 'our-principal', component: OurPrincipalComponent
                },
                {
                    path: 'mission-and-vission', component: MissionAndVisionComponent
                },
                {
                    path: 'faculty-member', component: FacultyMemberComponent
                }
            ]
        },

        {
            path: 'contact', component: ContactComponent
        },

    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],


})

export class UserRoutingModule { }