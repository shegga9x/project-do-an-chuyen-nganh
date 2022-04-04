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
  result: any[];
  finish: boolean = false;
  constructor(private titleService: Title, private courseManageService: CourseManageService) {
    this.titleService.setTitle("Home");
  }

  ngOnInit(): void {
    this.getSubAvailable();
  }

  getSubAvailable() {
    this.courseManageService.getSubAvailableRegist().subscribe(x => {
      this.result = x;
      this.finish = true;
      console.log(this.result[0]['clazzDTO']);

    });
  }
}
