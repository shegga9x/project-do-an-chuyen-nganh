import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-half-early-exam-result',
  templateUrl: './half-early-exam-result.component.html',
  styleUrls: ['./half-early-exam-result.component.scss']
})
export class HalfEarlyExamResultComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Half Early Exam Result");
  }

  ngOnInit(): void {
  }

}
