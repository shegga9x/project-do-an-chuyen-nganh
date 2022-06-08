import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CourseManageService } from 'src/app/services';

@Component({
  selector: 'app-time-table',
  templateUrl: './time-table.component.html',
  styleUrls: ['./time-table.component.scss']
})
export class TimeTableComponent implements OnInit {

  listTimeTableST: any[] = [];
  listTop3Semester:any[] = [];
  idSemester:string = "";
  loading: boolean = false;

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private router: Router

  ) {
    this.titleService.setTitle('Academic Calender');
  }

  ngOnInit(): void {
    this.courseManageService.getTop3SemesterTimeTableST().subscribe({
    next: (x: any) => {
        //foreach
        x.forEach((element: any) => {
          this.listTop3Semester.push(element);
        });
        this.idSemester = this.listTop3Semester[0];
        this.getTimeTableST(this.idSemester);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  getTimeTableST(idSemester:string) {
    this.courseManageService.getTimeTableSTRegist(idSemester).subscribe({
      next: (x: any) => {
        //foreach
        this.listTimeTableST = [];
        x.forEach((element: any) => {
          this.listTimeTableST.push(element);
        });
        //add to course-manage services
        this.courseManageService.listTimeTable_ST = this.listTimeTableST;
        this.loading = true;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getListStudentBySubject(idSCHEDULE: any) {
    console.log(idSCHEDULE);
    this.router.navigate(["user/student/dssv"], { queryParams: { idSCHEDULE: idSCHEDULE } });
  }

}
