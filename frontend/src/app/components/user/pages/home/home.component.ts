import { CourseManageService } from 'src/app/services/course-manage.service';
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

  constructor(
    private titleService: Title, private courseManageService: CourseManageService) {
    this.titleService.setTitle("Home");
  }

  ngOnInit(): void {
    this.getSubAvailable();
  }

  getSubAvailable() {

  }
}
