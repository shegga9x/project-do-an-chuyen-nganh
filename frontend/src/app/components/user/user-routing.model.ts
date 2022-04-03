import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//component
import { HomeComponent } from 'src/app/components/user/pages/home/home.component';
import { ContactComponent } from 'src/app/components/user/pages/contact/contact.component';
import { AboutUsComponent } from 'src/app/components/user/pages/about/about-us/about-us.component';
import { AboutComponent } from 'src/app/components/user/pages/about/about.component';
import { OurPrincipalComponent } from 'src/app/components/user/pages/about/our-principal/our-principal.component';
import { MissionAndVisionComponent } from 'src/app/components/user/pages/about/mission-and-vision/mission-and-vision.component';
import { FacultyMemberComponent } from 'src/app/components/user/pages/about/faculty-member/faculty-member.component';
import { AcademicComponent } from 'src/app/components/user/pages/academic/academic.component';
import { ClassRoutineComponent } from 'src/app/components/user/pages/academic/class-routine/class-routine.component';
import { AcademicEventsComponent } from 'src/app/components/user/pages/academic/academic-events/academic-events.component';
import { AcademicCalenderComponent } from 'src/app/components/user/pages/academic/academic-calender/academic-calender.component';
import { RulesAndRegulationsComponent } from 'src/app/components/user/pages/academic/rules-and-regulations/rules-and-regulations.component';
import { FacultyInformationsComponent } from 'src/app/components/user/pages/academic/faculty-informations/faculty-informations.component';
import { ExamRoutineComponent } from 'src/app/components/user/pages/exam-routine/exam-routine.component';
import { HalfEarlyExamComponent } from 'src/app/components/user/pages/exam-routine/half-early-exam/half-early-exam.component';
import { TutorialExamComponent } from 'src/app/components/user/pages/exam-routine/tutorial-exam/tutorial-exam.component';
import { FinalExamComponent } from 'src/app/components/user/pages/exam-routine/final-exam/final-exam.component';
import { SscExamComponent } from 'src/app/components/user/pages/exam-routine/ssc-exam/ssc-exam.component';
import { JscExamComponent } from 'src/app/components/user/pages/exam-routine/jsc-exam/jsc-exam.component';
import { ResultComponent } from 'src/app/components/user/pages/result/result.component';
import { HalfEarlyExamResultComponent } from 'src/app/components/user/pages/result/half-early-exam-result/half-early-exam-result.component';
import { TutorialExamResultComponent } from 'src/app/components/user/pages/result/tutorial-exam-result/tutorial-exam-result.component';
import { FinalExamResultComponent } from 'src/app/components/user/pages/result/final-exam-result/final-exam-result.component';
import { SscExamResultComponent } from 'src/app/components/user/pages/result/ssc-exam-result/ssc-exam-result.component';
import { JscExamResultComponent } from 'src/app/components/user/pages/result/jsc-exam-result/jsc-exam-result.component';
import { LibraryComponent } from 'src/app/components/user/pages/library/library.component';
import { UserComponent } from 'src/app/components/user/user.component';

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
            path: 'academic', component: AcademicComponent,
            children: [
                {
                    path: '', redirectTo: 'class-routine', pathMatch: 'full'
                },
                {
                    path: 'class-routine', component: ClassRoutineComponent
                },
                {
                    path: 'academic-event', component: AcademicEventsComponent
                },
                {
                    path: 'academic-calender', component: AcademicCalenderComponent
                },
                {
                    path: 'rules-and-regulations', component: RulesAndRegulationsComponent
                },
                {
                    path: 'faculty-information', component: FacultyInformationsComponent
                }
            ]
        },
        {
            path: 'exam-routine', component: ExamRoutineComponent,
            children: [
                {
                    path: '', redirectTo: 'half-early-exam', pathMatch: 'full'
                },
                {
                    path: 'half-early-exam', component: HalfEarlyExamComponent
                },
                {
                    path: 'tutorial-exam', component: TutorialExamComponent
                },
                {
                    path: 'final-exam', component: FinalExamComponent
                },
                {
                    path: 'ssc-exam', component: SscExamComponent
                },
                {
                    path: 'jsc-exam', component: JscExamComponent
                }
            ]
        },
        {
            path: 'result', component: ResultComponent,
            children: [
                {
                    path: '', redirectTo: 'half-early-exam', pathMatch: 'full',
                },
                {
                    path: 'half-early-exam', component: HalfEarlyExamResultComponent
                },
                {
                    path: 'tutorial-exam', component: TutorialExamResultComponent
                },
                {
                    path: 'final-exam', component: FinalExamResultComponent
                },
                {
                    path: 'ssc-exam', component: SscExamResultComponent
                },
                {
                    path: 'jsc-exam', component: JscExamResultComponent
                }
            ]
        },
        {
            path: 'contact', component: ContactComponent
        },
        {
            path: 'library', component: LibraryComponent
        }
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class UserRoutingModule { }