import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { GeneralService, CourseManageService } from 'src/app/services';

@Component({
  selector: 'app-class-routine',
  templateUrl: './class-routine.component.html',
  styleUrls: ['./class-routine.component.scss'],
})
export class ClassRoutineComponent implements OnInit {
  listSubAvailable: any[] = [];
  listCourseRegisterFake: any[] = [];

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private generalService: GeneralService
  ) {
    this.titleService.setTitle('Class Routine');
  }

  ngOnInit(): void {
    this.getListSubjectForProfressor();
  }

  getListSubjectForProfressor() {
    this.courseManageService.getListSubjectForProfressorRegist().subscribe({
      next: (x: any) => {
        this.listSubAvailable = x;
        //add to course-manage services
        this.courseManageService.listSubAvailable = this.listSubAvailable;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  findIdCourse(idCourse: string) {
    const even = (element: any) => {
      return element.idCourse == idCourse;
    };
    return this.listCourseRegisterFake.some(even);
  }

}
