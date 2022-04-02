import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-academic-events',
  templateUrl: './academic-events.component.html',
  styleUrls: ['./academic-events.component.scss']
})
export class AcademicEventsComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Academic Events");
  }

  ngOnInit(): void {
  }

}
