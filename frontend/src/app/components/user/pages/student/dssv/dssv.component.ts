import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { ActivatedRoute } from '@angular/router';
import { CourseManageService } from 'src/app/services';

@Component({
  selector: 'app-dssv',
  templateUrl: './dssv.component.html',
  styleUrls: ['./dssv.component.scss']
})
export class DssvComponent implements OnInit {

  listStudent: any[] = []
  listStudentAfterSort: any[] = []
  constructor(private titleService: Title, private route: ActivatedRoute, private courseManageService: CourseManageService) {
    this.titleService.setTitle("Half Early Exam");
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.getListStudentBySubject(params.idSCHEDULE)


    });
  }
  getListStudentBySubject(idSCHEDULE: any) {
    this.courseManageService
      .getListStudentBySubjectRegist(idSCHEDULE)
      .subscribe({
        next: (x: any) => {
          this.listStudent = x
          this.listStudentAfterSort = x;
          console.log(x)
        },
        error: (error) => {
          console.log(error);
        },
      });
  }
  handleChange(event: any, field: string) {
    this.listStudentAfterSort = this.listStudent.sort((a, b) => {
        if (a[field] < b[field]) {
          return -1;
        }
        if (a[field] > b[field]) {
          return 1;
        }
        return 0;
      });
  }
}
