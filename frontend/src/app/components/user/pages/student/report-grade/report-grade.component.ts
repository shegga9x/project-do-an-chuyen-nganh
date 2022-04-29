import { ReactiveFormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService } from 'src/app/services';

@Component({
  selector: 'app-report-grade',
  templateUrl: './report-grade.component.html',
  styleUrls: ['./report-grade.component.scss'],
})
export class ReportGradeComponent implements OnInit {

  listIDSemester: any[] = [];
  listSemesterResults: any[] = [];

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
  ) {
    this.titleService.setTitle('Report Grade');
  }

  ngOnInit(): void {
    this.getIDSemesterST();
    this.getAllSemesterResults();
  }

  getIDSemesterST() {
    this.courseManageService.getIDSemesterST().subscribe({
      next: (x: any) => {
        this.listIDSemester = x;
        //add to course-manage services
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getAllSemesterResults() {
    this.courseManageService.getSemesterReusltRegist().subscribe({
      next: (x: any) => {
        this.listSemesterResults = x;
        console.log(x);
        //add to course-manage services
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getSemesterResultsByID(idSemester: string) {
    return this.listSemesterResults.filter(x => x.id_Semester == idSemester);
  }

  getTotalScoreSemester(idSemester: string) {
    const sum = this.listSemesterResults.filter(x => x.id_Semester == idSemester)
      .reduce((sum, current) => sum + current.total, 0);
    console.log(sum);
    console.log("hellooo");
  }
  getTotalScoreSystem4Semester(idSemester: string) {

  }
}
