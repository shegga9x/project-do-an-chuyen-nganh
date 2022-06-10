import { GeneralService, PDTService } from 'src/app/services';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-close-course-regist',
  templateUrl: './close-course-regist.component.html',
  styleUrls: ['./close-course-regist.component.scss'],
})
export class CloseCourseRegistComponent implements OnInit {
  listCourseRegist: any[] = [];
  listCloseCourseRegist: any[] = [];
  constructor(private pDTService: PDTService) {}

  ngOnInit(): void {
    this.getListCourseRegist();
    this.getListCloseCourseRegist();
  }

  getListCourseRegist() {
    this.pDTService.getCourseRegist().subscribe({
      next: (x: any) => {
        console.log(x);
        this.listCourseRegist = x;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  getListCloseCourseRegist() {
    this.pDTService.getCloseCourseRegist().subscribe({
      next: (x: any) => {
        console.log(x);
        this.listCloseCourseRegist = x;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  checkCloseCourseRegist(idSchedule: string) {
    return this.listCloseCourseRegist.some((element) => {
      return element.scheduleDTO.idSchedule == idSchedule;
    });
  }

  deletCourseRegist() {
    const cac = [
      ...new Set(
        this.listCloseCourseRegist.map(
          (data) => data.courseOfferingDTO.idCourseOffering
        )
      ),
    ];
    this.pDTService.deleteCourseOffering(cac).subscribe({
      next: (obj: any) => {
        this.getListCourseRegist();
        alert(obj.message);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
