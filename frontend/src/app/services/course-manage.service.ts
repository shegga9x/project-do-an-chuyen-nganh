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


  constructor(private http: HttpClient, private accountService: AccountService) { }

  getSubAvailableRegist() {
    let params = { id: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_sub_available_st/`, {
      params: params,
    });
  }

  getSemesterReusltRegist() {
    let params = { idStudent: '18130005', idSemester: '2021_1' };
    return this.http.get(`${baseUrl}/get_semester_reuslt/`, {
      params: params,
    });
  }

  submitCourseRegisterFake(idCourseOffering: string) {
    return this.http.post(`${baseUrl}/submit_course_register_fake`, idCourseOffering);
  }

  getCourseRegisterFake() {
    let params = { id: this.accountService.accountValue?.idAccount as string };
    return this.http.get(`${baseUrl}/get_course_register_fake/`, {
      params: params,
    });
  }

  submitCourseRegist(listCourseRegistRequests: Map<string, boolean>) {
    const convMap: any = {};
    listCourseRegistRequests.forEach((val: boolean, key: string) => {
      convMap[key] = val;
    });
    console.log(convMap);
    return this.http.post(`${baseUrl}/submit_course_regist`, convMap);
  }


}
