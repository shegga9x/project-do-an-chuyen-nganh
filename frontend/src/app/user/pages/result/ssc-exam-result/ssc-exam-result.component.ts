import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-ssc-exam-result',
  templateUrl: './ssc-exam-result.component.html',
  styleUrls: ['./ssc-exam-result.component.scss']
})
export class SscExamResultComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Ssc Exam Result");
  }

  ngOnInit(): void {
  }

}
