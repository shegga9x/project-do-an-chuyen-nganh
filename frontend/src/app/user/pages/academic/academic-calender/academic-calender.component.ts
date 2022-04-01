import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-academic-calender',
  templateUrl: './academic-calender.component.html',
  styleUrls: ['./academic-calender.component.scss']
})
export class AcademicCalenderComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Academic Calender");
  }

  ngOnInit(): void {
  }

}
