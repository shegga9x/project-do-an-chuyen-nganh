import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { CourseManageService, GeneralService } from 'src/app/services';


@Component({
  selector: 'app-report-school-fees',
  templateUrl: './report-school-fees.component.html',
  styleUrls: ['./report-school-fees.component.scss']
})
export class ReportSchoolFeesComponent implements OnInit {
  content: string = "Thu Học Phí HK 2 (2021-2022)";
  id: string = "12495638";
  detail: string = "";
  total: number = 0;
  // listCourseRegistRequests: Map<string, boolean> = new Map<string, boolean>();
  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private generalService: GeneralService
  ) {
    this.titleService.setTitle('Academic Events');
  }
  ngOnInit(): void {

    this.getListCourseRegisterFake();
  }
  getListCourseRegisterFake() {
    this.courseManageService.getCourseRegisterFake().subscribe({

      next: (x: any) => {
      
        let list: any[] = x;
        console.log(list)
        list.forEach(element=>{
          if(element.status == "Đã lưu vào CSDL"){
            this.detail += `${element.nameCourse} (${element.idCourse}),`  ;
            this.total += (element.courseCertificate * 360);

          }
        })
      },
      error: (error) => {
        console.log(error);
      },
    });
  }





}
