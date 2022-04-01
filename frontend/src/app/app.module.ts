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
import { HeaderComponent } from './user/commons/header/header.component';
import { FooterComponent } from './user/commons/footer/footer.component';
import { HeaderBarComponent } from './user/commons/header-bar/header-bar.component';
import { HomeComponent } from './user/pages/home/home.component';
import { ContactComponent } from './user/pages/contact/contact.component';
import { ModalLoginComponent } from './user/commons/modal-login/modal-login.component';
import { AboutUsComponent } from './user/pages/about/about-us/about-us.component';
import { AboutComponent } from './user/pages/about/about.component';
import { OurPrincipalComponent } from './user/pages/about/our-principal/our-principal.component';
import { MissionAndVisionComponent } from './user/pages/about/mission-and-vision/mission-and-vision.component';
import { FacultyMemberComponent } from './user/pages/about/faculty-member/faculty-member.component';
import { AcademicComponent } from './user/pages/academic/academic.component';
import { ClassRoutineComponent } from './user/pages/academic/class-routine/class-routine.component';
import { AcademicEventsComponent } from './user/pages/academic/academic-events/academic-events.component';
import { AcademicCalenderComponent } from './user/pages/academic/academic-calender/academic-calender.component';
import { RulesAndRegulationsComponent } from './user/pages/academic/rules-and-regulations/rules-and-regulations.component';
import { FacultyInformationsComponent } from './user/pages/academic/faculty-informations/faculty-informations.component';
import { ExamRoutineComponent } from './user/pages/exam-routine/exam-routine.component';
import { HalfEarlyExamComponent } from './user/pages/exam-routine/half-early-exam/half-early-exam.component';
import { TutorialExamComponent } from './user/pages/exam-routine/tutorial-exam/tutorial-exam.component';
import { FinalExamComponent } from './user/pages/exam-routine/final-exam/final-exam.component';
import { SscExamComponent } from './user/pages/exam-routine/ssc-exam/ssc-exam.component';
import { JscExamComponent } from './user/pages/exam-routine/jsc-exam/jsc-exam.component';
import { ResultComponent } from './user/pages/result/result.component';
import { HalfEarlyExamResultComponent } from './user/pages/result/half-early-exam-result/half-early-exam-result.component';
import { TutorialExamResultComponent } from './user/pages/result/tutorial-exam-result/tutorial-exam-result.component';
import { FinalExamResultComponent } from './user/pages/result/final-exam-result/final-exam-result.component';
import { SscExamResultComponent } from './user/pages/result/ssc-exam-result/ssc-exam-result.component';
import { JscExamResultComponent } from './user/pages/result/jsc-exam-result/jsc-exam-result.component';
import { LibraryComponent } from './user/pages/library/library.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import { DialogLoginComponent } from './user/shared/dialog-login/dialog-login.component';

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
