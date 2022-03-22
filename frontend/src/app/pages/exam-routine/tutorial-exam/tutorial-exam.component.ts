import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-tutorial-exam',
  templateUrl: './tutorial-exam.component.html',
  styleUrls: ['./tutorial-exam.component.scss']
})
export class TutorialExamComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Tutorial Exam");
  }

  ngOnInit(): void {
  }

}
