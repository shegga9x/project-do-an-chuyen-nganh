import { Component, OnInit } from '@angular/core';
import { CourseManageService } from 'src/app/services';

@Component({
  selector: 'app-report-date-exam',
  templateUrl: './report-date-exam.component.html',
  styleUrls: ['./report-date-exam.component.scss']
})
export class ReportDateExamComponent implements OnInit {
  
  listSemester: any[] = [];
  listDateExam: any[] = [];
  constructor(private courseManageService: CourseManageService) { }

  ngOnInit(): void {
    this.getSemesterByIdStudent();
  }
  getSemesterByIdStudent(){
    this.courseManageService.getSemesterByIdStudent().subscribe((res: any) => {
      this.listSemester=res;
      console.log(this.listSemester);
      this.getDateExam(this.listSemester[this.listSemester.length-1].idSemester);
    })
  }
  getDateExam(IdSemester: string){
    this.courseManageService.getDateExam(IdSemester).subscribe((res: any) =>{
      this.listDateExam=res.dateExamDTO;
      console.log(this.listDateExam);
    })
  }
  checkLastestSemester(idSemester: string){
    console.log(idSemester);
    if (idSemester == this.listSemester[this.listSemester.length-1].idSemester) {
      return true;
    }
    return false;
  }

}
