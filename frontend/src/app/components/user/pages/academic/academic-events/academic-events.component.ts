import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Schedule } from 'src/app/models/schedule';
import { Faculty } from 'src/app/models/faculty';
import { CourseOffering } from 'src/app/models/courseOffering';
import { Course } from 'src/app/models/course';
import { Clazz } from 'src/app/models/clazz';
import { CourseManageService } from 'src/app/services/course-manage.service';

@Component({
  selector: 'app-academic-events',
  templateUrl: './academic-events.component.html',
  styleUrls: ['./academic-events.component.scss'],
})
export class AcademicEventsComponent implements OnInit {
  clazzs: Clazz[] = [];
  courses: Course[] = [];
  courseOfferings: CourseOffering[] = [];
  facultys: Faculty[] = [];
  schedules: Schedule[] = [];
  finish: boolean = false;
  listCourseRegistRequests: Map<string, boolean> = new Map<string, boolean>();
  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService
  ) {
    this.titleService.setTitle('Academic Events');
  }

  ngOnInit(): void {
    this.getSubAvailable();
    //this.getSemesterReuslt();
  }
  updateList(id: string, idSchedule : string,checked: HTMLInputElement): void {
    this.listCourseRegistRequests.set(idSchedule, checked.checked);
  }

  submit() {
    this.courseManageService.submitCourseRegist(this.listCourseRegistRequests)
  }
  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe({
      next: (x: any) => {
        x.forEach((element: any) => {
          console.log(element);
          let clazz: Clazz = element['clazzDTO'];
          let course: Course = element['courseDTO'];
          let courseOffering: CourseOffering = element['courseOfferingDTO'];
          let faculty: Faculty = element['facultyDTO'];
          let schedule: Schedule = element['scheduleDTO'];
          this.clazzs.push(clazz);
          this.courses.push(course);
          this.courseOfferings.push(courseOffering);
          this.facultys.push(faculty);
          this.schedules.push(schedule);
        });
        this.finish = true;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
  
  // getSemesterReuslt() {
  //   console.log("alo nghe ko")
  //   this.courseManageService
  //     .getSemesterReusltRegist()
  //     .subscribe((x) => console.log(x));
  // }

}
