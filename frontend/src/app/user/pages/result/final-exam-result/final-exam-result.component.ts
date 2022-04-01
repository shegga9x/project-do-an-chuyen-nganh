import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-final-exam-result',
  templateUrl: './final-exam-result.component.html',
  styleUrls: ['./final-exam-result.component.scss']
})
export class FinalExamResultComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Final Exam Result");
  }

  ngOnInit(): void {
  }

}
