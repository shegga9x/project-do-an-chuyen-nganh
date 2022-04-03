import { Schedule } from './../../../../models/schedule';
import { Faculty } from './../../../../models/faculty';
import { CourseOffering } from './../../../../models/courseOffering';
import { Course } from './../../../../models/course';
import { Clazz } from './../../../../models/clazz';
import { CourseManageService } from './../../../../services/course-manage.service';
import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  clazz: Clazz[] = [];
  course: Course[] = [];
  courseOffering: CourseOffering[] = [];
  faculty: Faculty[] = [];
  schedule: Schedule[] = [];
  finish: boolean = false;

  constructor(private titleService: Title, private courseManageService: CourseManageService) {
    this.titleService.setTitle("Home");
  }

  ngOnInit(): void {
    this.getSubAvailable();
  }
  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe({
      next: (x: any) => {
        x.forEach((element: any) => {
          console.log(element)
          let clazz: Clazz = element['clazzDTO'];
          let course: Course = element['courseDTO'];
          let courseOffering: CourseOffering = element['courseOfferingDTO'];
          let faculty: Faculty = element['facultyDTO'];
          let schedule: Schedule = element['scheduleDTO'];
          this.clazz.push(clazz);
          this.course.push(course);
          this.courseOffering.push(courseOffering);
          this.faculty.push(faculty);
          this.schedule.push(schedule);
        });
        this.finish = true;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
