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

  listTimeTablePR: any[] = [];
  loading: boolean = false;

  constructor( private titleService: Title,
    private courseManageService: CourseManageService,
    private router: Router) {
    this.titleService.setTitle("Rules And Regulation");
  }
  ngOnInit(): void {
    this.getTimeTablePR();
  }

  getTimeTablePR() {
    this.courseManageService.getTimeTablePRRegist().subscribe({
      next: (x: any) => {
        //foreach
        x.forEach((element: any) => {
          console.log(element);
          this.listTimeTablePR.push(element);
        });
        //add to course-manage services
        this.courseManageService.listTimeTable_ST = this.listTimeTablePR;
        this.loading = true;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getListStudentBySubject(idSCHEDULE: any) {
    console.log(idSCHEDULE);
    this.router.navigate(["user/dssv"], { queryParams: { idSCHEDULE: idSCHEDULE } });
  }

}
