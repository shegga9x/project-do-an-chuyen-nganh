import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-ssc-exam',
  templateUrl: './ssc-exam.component.html',
  styleUrls: ['./ssc-exam.component.scss']
})
export class SscExamComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Ssc Exam");
  }

  ngOnInit(): void {
  }

}
