import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-half-early-exam',
  templateUrl: './half-early-exam.component.html',
  styleUrls: ['./half-early-exam.component.scss']
})
export class HalfEarlyExamComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Half Early Exam");
  }

  ngOnInit(): void {
  }

}
