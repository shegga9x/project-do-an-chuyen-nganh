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
  loading: boolean = false;

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private router: Router

  ) {
    this.titleService.setTitle('Academic Calender');
  }

  ngOnInit(): void {
    this.getTimeTableST();
  }

  getTimeTableST() {
    this.courseManageService.getTimeTableSTRegist().subscribe({
      next: (x: any) => {
        //foreach
        x.forEach((element: any) => {
          console.log(element);
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
    this.router.navigate(["user/list-student-ttb"], { queryParams: { idSCHEDULE: idSCHEDULE } });
    // this.courseManageService
    //   .getListStudentBySubjectRegist(idSCHEDULE)
    //   .subscribe({
    //     next: (x: any) => {
    //       //foreach
    //       x.forEach((element: any) => {
    //         console.log(element);
    //       });
    //     },
    //     error: (error) => {
    //       console.log(error);
    //     },
    //   });
  }

}
