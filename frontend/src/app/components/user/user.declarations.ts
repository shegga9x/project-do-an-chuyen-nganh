//  import * as homeComponent from './../admin/pages/home/home.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { HeaderComponent } from 'src/app/components/user/commons/header/header.component';
import { FooterComponent } from 'src/app/components/user/commons/footer/footer.component';
import { HeaderBarComponent } from 'src/app/components/user/commons/header-bar/header-bar.component';

import { UserComponent } from 'src/app/components/user/user.component';
import { AdminComponent } from 'src/app/components/admin/admin.component';
import { DialogLoginComponent } from 'src/app/components/user/shared/dialog-login/dialog-login.component';

import { CourseRegistComponent } from './pages/student/course-regist/course-regist.component';
import { ReportDateExamComponent } from './pages/student/report-date-exam/report-date-exam.component';
import { ReportGradeComponent } from './pages/student/report-grade/report-grade.component';
import { ReportSchoolFeesComponent } from './pages/student/report-school-fees/report-school-fees.component';
import { TimeTableComponent } from './pages/student/time-table/time-table.component';
import { StudentComponent } from './pages/student/student.component';
import { AutoImportAccountComponent } from '../admin/pages/auto-import-account/auto-import-account.component';
import { AutoImportGradeComponent } from '../admin/pages/auto-import-grade/auto-import-grade.component';
import { CloseCourseRegistComponent } from '../admin/pages/close-course-regist/close-course-regist.component';
import { PagesComponent } from '../admin/pages/pages.component';
import { SharedComponent } from '../admin/shared/shared.component';
import { ManageEntitesComponent } from '../admin/pages/manage-entites/manage-entites.component';
import { AboutUsComponent } from './pages/commons/about/about-us/about-us.component';
import { AboutComponent } from './pages/commons/about/about.component';
import { FacultyMemberComponent } from './pages/commons/about/faculty-member/faculty-member.component';
import { MissionAndVisionComponent } from './pages/commons/about/mission-and-vision/mission-and-vision.component';
import { OurPrincipalComponent } from './pages/commons/about/our-principal/our-principal.component';
import { ContactComponent } from './pages/commons/contact/contact.component';
import { DeclareComponent } from './pages/commons/declare/declare.component';
import { ListStudentTTBComponent } from './pages/commons/list-student-ttb/list-student-ttb.component';
import { PageNotFoundComponent } from './pages/commons/page-not-found/page-not-found.component';
import { HomeComponent } from './pages/commons/home/home.component';

import * as Professor from './pages/professor/course-regist/course-regist.component';

export const UserDeclarations = [
    ListStudentTTBComponent,
    ManageEntitesComponent,
    PagesComponent,
    SharedComponent,
    CloseCourseRegistComponent,
    AutoImportGradeComponent,
    AutoImportAccountComponent,
    StudentComponent,
    ProfessorComponent,
    CourseRegistComponent,
    TimeTableComponent,
    ReportGradeComponent,
    ReportSchoolFeesComponent,
    ReportDateExamComponent,
    DeclareComponent,
    HeaderComponent,
    FooterComponent,
    HeaderBarComponent,
    // homeComponent.HomeComponent,
    ContactComponent,
    AboutUsComponent,
    AboutComponent,
    OurPrincipalComponent,
    MissionAndVisionComponent,
    FacultyMemberComponent,
    UserComponent,
    AdminComponent,
    DialogLoginComponent,
    PageNotFoundComponent,
    HomeComponent,

    Professor.CourseRegistComponent,
]