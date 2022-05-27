import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService } from 'src/app/services';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-course-program',
  templateUrl: './course-program.component.html',
  styleUrls: ['./course-program.component.scss']
})
export class CourseProgramComponent implements OnInit {

  listCourseProgram: any[] = []

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService
  ) {
    this.titleService.setTitle('Course Program');
  }

  ngOnInit(): void {
    this.getCourseProgram();
  }

  getCourseProgram() {
    this.courseManageService.getCourseProgram().subscribe({
      next: (x: any) => {
        this.listCourseProgram = x;
        this.listCourseProgram.sort((a, b) => {
          if (a.stc > b.stc) {
            return 1;
          }
          if (a.stc < b.stc) {
            return -1;
          }
          if (a.stc == b.stc) {
            if (a.semester > b.semester) {
              return 1;
            }
            if (a.semester < b.semester) {
              return -1;
            }
          }
          return 0;
        });
        console.log(this.listCourseProgram);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
  exportToExcel() {
    const workSheet = XLSX.utils.json_to_sheet(this.listCourseProgram);
    const workBook: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workBook, workSheet, 'SheetName');
    XLSX.writeFile(workBook, 'CTĐT.xlsx');
  }
}
