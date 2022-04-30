import { ReactiveFormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService } from 'src/app/services';
import { forkJoin, Observable } from 'rxjs';

@Component({
  selector: 'app-report-grade',
  templateUrl: './report-grade.component.html',
  styleUrls: ['./report-grade.component.scss'],
})
export class ReportGradeComponent implements OnInit {

  listIDSemester: any[] = [];
  listSemesterResults: any[] = [];
  listAllSemesterResults: any[] = [];

  listSemesterResultsByIDSemester: any[] = [];

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
  ) {
    this.titleService.setTitle('Report Grade');
  }

  ngOnInit(): void {
    const combined = forkJoin(
      [this.getIDSemesterST(),
      this.getAllSemesterResults(),
      this.getGradeAVReuslt()]
    )

    combined.subscribe((latestValues: any) => {
      this.listIDSemester = latestValues[0];
      this.listSemesterResults = latestValues[1];
      this.listAllSemesterResults = latestValues[2];
      // console.log(latestValues);
      // console.log(this.listIDSemester);
      // console.log(this.listSemesterResults);
      // console.log(this.listAllSemesterResults);
      this.getAllSemesterResults().subscribe((data: any) => { this.listSemesterResults = data });
      this.listIDSemester.forEach(element => {
        this.listSemesterResultsByIDSemester.push(this.listSemesterResults.filter(x => x.id_Semester == element.idSemester));
      });

    });

  }

  getIDSemesterST() {
    return this.courseManageService.getIDSemesterST();
  }

  getAllSemesterResults() {
    return this.courseManageService.getSemesterReuslt();
  }

  getSemesterResultsByID(idSemester: string) {
    return this.listSemesterResults.filter(x => x.id_Semester == idSemester);
  }


  getGradeAVReuslt() {
    return this.courseManageService.getGradeAVSemesterReuslt();
  }

  getGradeAVReusltByIDSemester(idSemester: string) {
    return this.listAllSemesterResults.filter(x => x.idSemester == idSemester);
  }

  getSemesterResultsByIDSemesterALL(idSemester: string) {
    let totalSTC = 0;
    let totalScoreForSTC = 0;
    this.listSemesterResultsByIDSemester.forEach(element => {
      if (element[0].id_Semester <= idSemester) {
        element.forEach((element1: any) => {
          if (element1.score < 4) {
            element1.score = 0;
          }
          totalSTC += element1.course_certificate;
          totalScoreForSTC += element1.course_certificate * element1.score;
        });
      }
    });
    return Number((totalScoreForSTC / totalSTC).toFixed(2));
  }

  getSTC_byIDSemesterALL(idSemester: string) {
    let totalSTC = 0;
    this.listSemesterResultsByIDSemester.forEach(element => {
      if (element[0].id_Semester <= idSemester) {
        element.forEach((element1: any) => {
          if (element1.score < 4) {
            element1.course_certificate = 0;
          }
          totalSTC += element1.course_certificate;
        });
      }
    });
    return Number((totalSTC));
  }
}
