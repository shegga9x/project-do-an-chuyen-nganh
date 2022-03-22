import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//component
import { HomeComponent } from './pages/home/home.component';
import { AboutUsComponent } from './pages/about/about-us/about-us.component';
import { AboutComponent } from './pages/about/about.component';
import { OurPrincipalComponent } from './pages/about/our-principal/our-principal.component';
import { MissionAndVisionComponent } from './pages/about/mission-and-vision/mission-and-vision.component';
import { FacultyMemberComponent } from './pages/about/faculty-member/faculty-member.component';
import { ContactComponent } from './pages/contact/contact.component';
import { AcademicComponent } from './pages/academic/academic.component';
import { ClassRoutineComponent } from './pages/academic/class-routine/class-routine.component';
import { AcademicEventsComponent } from './pages/academic/academic-events/academic-events.component';
import { AcademicCalenderComponent } from './pages/academic/academic-calender/academic-calender.component';
import { RulesAndRegulationsComponent } from './pages/academic/rules-and-regulations/rules-and-regulations.component';
import { FacultyInformationsComponent } from './pages/academic/faculty-informations/faculty-informations.component';
import { ExamRoutineComponent } from './pages/exam-routine/exam-routine.component';
import { HalfEarlyExamComponent } from './pages/exam-routine/half-early-exam/half-early-exam.component';
import { TutorialExamComponent } from './pages/exam-routine/tutorial-exam/tutorial-exam.component';
import { FinalExamComponent } from './pages/exam-routine/final-exam/final-exam.component';
import { SscExamComponent } from './pages/exam-routine/ssc-exam/ssc-exam.component';
import { JscExamComponent } from './pages/exam-routine/jsc-exam/jsc-exam.component';
import { ResultComponent } from './pages/result/result.component';
import { HalfEarlyExamResultComponent } from './pages/result/half-early-exam-result/half-early-exam-result.component';
import { TutorialExamResultComponent } from './pages/result/tutorial-exam-result/tutorial-exam-result.component';
import { FinalExamResultComponent } from './pages/result/final-exam-result/final-exam-result.component';
import { SscExamResultComponent } from './pages/result/ssc-exam-result/ssc-exam-result.component';
import { JscExamResultComponent } from './pages/result/jsc-exam-result/jsc-exam-result.component';
import { LibraryComponent } from './pages/library/library.component';

const routes: Routes = [
  {
    path: '', redirectTo: 'home', pathMatch: 'full'
  },
  {
    path: 'home', component: HomeComponent
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
