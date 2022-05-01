import { AccountService } from './account.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const baseUrl = `${environment.apiUrl}/course-manage`;

@Injectable({
  providedIn: 'root',
})
export class CourseManageService {
  listSubAvailable: any[] = [];
  listTimeTable_ST: any[] = [];

  constructor(
    private http: HttpClient,
    private accountService: AccountService
  ) { }

  getSubAvailableRegist() {
    let params = { id: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_sub_available_st/`, {
      params: params,
    });
  }

  submitCourseRegisterFake(idCourseOffering: string) {
    return this.http.post(`${baseUrl}/submit_course_register_fake`,
      idCourseOffering
    );
  }

  deleteCourseRegister(listIdCourse: string[]) {
    return this.http.post(`${baseUrl}/delete_course_register`, listIdCourse);
  }

  getCourseRegisterFake() {
    let params = { id: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_course_register_fake/`, {
      params: params,
    });
  }

  submitCourseRegist() {
    return this.http.get(`${baseUrl}/submit_course_regist`);
  }

  getTimeTableSTRegist() {
    let params = { idACCOUNT: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_time_table_st/`, {
      params: params,
    });
  }

  getListStudentBySubjectRegist(id: any) {
    let params = { idSchedule: id };
    return this.http.get(`${baseUrl}/get_list_student_by_subject/`, {
      params: params,
    });
  }

  // Profressor
  getListSubjectForProfressorRegist() {
    let params = { idProfessor: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_list_subject_for_professor/`, {
      params: params,
    });
  }

  submitCourseForProfessor(idCourseOffering: string) {
    return this.http.post(`${baseUrl}/submit_course_for_professor`, idCourseOffering);
  }

  getCourseRegisterFakeForProfessor() {
    let params = { idProfessor: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_course_register_fake_for_professor/`, {
      params: params,
    });
  }

  deleteCourseRegisterForProfessor(listIdCourse: string[]) {
    return this.http.post(`${baseUrl}/delete_course_register_for_professor`, listIdCourse);
  }

  getTimeTablePRRegist() {
    let params = { idACCOUNT: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_time_table_professor/`, {
      params: params,
    });
  }

  // Xem điểm cuối kì của ST
  getIDSemesterST() {
    let params = { idACCOUNT: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_id_semester/`, {
      params: params,
    });
  }

  getSemesterReuslt() {
    let params = { idStudent: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_semester_reuslt/`, {
      params: params,
    });
  }

  getGradeAVSemesterReuslt() {
    let params = { idStudent: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_grade_av_semester_reuslt/`, {
      params: params,
    });
  }
  getDateExam(ID_Semester: string){
    let params = { idACCOUNT: this.accountService.accountValue?.idAccount as string, ID_Semester: ID_Semester };
    return this.http.get(`${baseUrl}/get_Date_Exam_ST/`, {
      params: params,
    });
  }
  getSemesterByIdStudent(){
    let params = { idACCOUNT: this.accountService.accountValue?.idAccount as string};
    return this.http.get(`${baseUrl}/get_semester_by_id_student`, {
      params: params,
    });
  }
}
