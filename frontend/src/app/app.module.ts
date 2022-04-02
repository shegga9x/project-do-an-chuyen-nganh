import { NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { appInitializer, ErrorInterceptor, JwtInterceptor } from 'src/app/helpers';
import { AccountService } from 'src/app/services';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule} from '@angular/material/dialog';


//component
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/user/commons/header/header.component';
import { FooterComponent } from './components/user/commons/footer/footer.component';
import { HeaderBarComponent } from './components/user/commons/header-bar/header-bar.component';
import { HomeComponent } from './components/user/pages/home/home.component';
import { ContactComponent } from './components/user/pages/contact/contact.component';
import { AboutUsComponent } from './components/user/pages/about/about-us/about-us.component';
import { AboutComponent } from './components/user/pages/about/about.component';
import { OurPrincipalComponent } from './components/user/pages/about/our-principal/our-principal.component';
import { MissionAndVisionComponent } from './components/user/pages/about/mission-and-vision/mission-and-vision.component';
import { FacultyMemberComponent } from './components/user/pages/about/faculty-member/faculty-member.component';
import { AcademicComponent } from './components/user/pages/academic/academic.component';
import { ClassRoutineComponent } from './components/user/pages/academic/class-routine/class-routine.component';
import { AcademicEventsComponent } from './components/user/pages/academic/academic-events/academic-events.component';
import { AcademicCalenderComponent } from './components/user/pages/academic/academic-calender/academic-calender.component';
import { RulesAndRegulationsComponent } from './components/user/pages/academic/rules-and-regulations/rules-and-regulations.component';
import { FacultyInformationsComponent } from './components/user/pages/academic/faculty-informations/faculty-informations.component';
import { ExamRoutineComponent } from './components/user/pages/exam-routine/exam-routine.component';
import { HalfEarlyExamComponent } from './components/user/pages/exam-routine/half-early-exam/half-early-exam.component';
import { TutorialExamComponent } from './components/user/pages/exam-routine/tutorial-exam/tutorial-exam.component';
import { FinalExamComponent } from './components/user/pages/exam-routine/final-exam/final-exam.component';
import { SscExamComponent } from './components/user/pages/exam-routine/ssc-exam/ssc-exam.component';
import { JscExamComponent } from './components/user/pages/exam-routine/jsc-exam/jsc-exam.component';
import { ResultComponent } from './components/user/pages/result/result.component';
import { HalfEarlyExamResultComponent } from './components/user/pages/result/half-early-exam-result/half-early-exam-result.component';
import { TutorialExamResultComponent } from './components/user/pages/result/tutorial-exam-result/tutorial-exam-result.component';
import { FinalExamResultComponent } from './components/user/pages/result/final-exam-result/final-exam-result.component';
import { SscExamResultComponent } from './components/user/pages/result/ssc-exam-result/ssc-exam-result.component';
import { JscExamResultComponent } from './components/user/pages/result/jsc-exam-result/jsc-exam-result.component';
import { LibraryComponent } from './components/user/pages/library/library.component';
import { UserComponent } from './components/user/user.component';
import { AdminComponent } from './components/admin/admin.component';
import { DialogLoginComponent } from './components/user/shared/dialog-login/dialog-login.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HeaderBarComponent,
    HomeComponent,
    ContactComponent,
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
    LibraryComponent,
    UserComponent,
    AdminComponent,
    DialogLoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, multi: true, deps: [AccountService] },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
