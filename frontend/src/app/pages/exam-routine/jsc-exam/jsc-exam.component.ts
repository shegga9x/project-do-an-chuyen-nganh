import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-jsc-exam',
  templateUrl: './jsc-exam.component.html',
  styleUrls: ['./jsc-exam.component.scss']
})
export class JscExamComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Jsc Exam");
  }

  ngOnInit(): void {
  }

}
