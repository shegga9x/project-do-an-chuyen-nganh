import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-jsc-exam-result',
  templateUrl: './jsc-exam-result.component.html',
  styleUrls: ['./jsc-exam-result.component.scss']
})
export class JscExamResultComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Jsc Exam Result");
  }

  ngOnInit(): void {
  }

}
