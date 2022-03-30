import { NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { appInitializer, ErrorInterceptor, JwtInterceptor } from 'src/app/helpers';
import { AccountService } from 'src/app/services';


//component
import { AppComponent } from './app.component';
import { HeaderComponent } from './commons/header/header.component';
import { FooterComponent } from './commons/footer/footer.component';
import { HeaderBarComponent } from './commons/header-bar/header-bar.component';
import { HomeComponent } from './pages/home/home.component';
import { ContactComponent } from './pages/contact/contact.component';
import { ModalLoginComponent } from './commons/modal-login/modal-login.component';
import { AboutUsComponent } from './pages/about/about-us/about-us.component';
import { AboutComponent } from './pages/about/about.component';
import { OurPrincipalComponent } from './pages/about/our-principal/our-principal.component';
import { MissionAndVisionComponent } from './pages/about/mission-and-vision/mission-and-vision.component';
import { FacultyMemberComponent } from './pages/about/faculty-member/faculty-member.component';
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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HeaderBarComponent,
    HomeComponent,
    ContactComponent,
    ModalLoginComponent,
    AboutUsComponent,
    AboutComponent,
    OurPrincipalComponent,
    MissionAndVisionComponent,
    FacultyMemberComponent,
    AcademicComponent,
    ClassRoutineComponent,
    AcademicEventsComponent,
    AcademicCalenderComponent,
    RulesAndRegulationsComponent,
    FacultyInformationsComponent,
    ExamRoutineComponent,
    HalfEarlyExamComponent,
    TutorialExamComponent,
    FinalExamComponent,
    SscExamComponent,
    JscExamComponent,
    ResultComponent,
    HalfEarlyExamResultComponent,
    TutorialExamResultComponent,
    FinalExamResultComponent,
    SscExamResultComponent,
    JscExamResultComponent,
    LibraryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, multi: true, deps: [AccountService] },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }

    // provider used to create fake backend
    //fakeBackendProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
