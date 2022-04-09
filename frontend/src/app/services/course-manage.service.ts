import { map } from 'rxjs/operators';
import { AccountService } from './account.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const baseUrl = `${environment.apiUrl}/course-manage`;

@Injectable({
  providedIn: 'root',
})
export class CourseManageService {

  listSubAvaliable: any[] = [];
  listTimeTable_ST: any[] = [];


  constructor(private http: HttpClient, private service: AccountService) { }

  getSubAvailableRegist() {
    let params = { id: '18130077' };
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

  submitCourseRegist(listCourseRegistRequests: Map<string, boolean>) {
    const convMap: any = {};
    listCourseRegistRequests.forEach((val: boolean, key: string) => {
      convMap[key] = val;
    });
    console.log(convMap);
    return this.http.post(`${baseUrl}/submit_course_regist`, convMap).subscribe(x => console.log(x));
  }

  getTimeTableSTRegist(){
    let params = { idACCOUNT: '18130005'};
    return this.http.get(`${baseUrl}/get_time_table_st/`, {
      params: params,
    });
  }


}
