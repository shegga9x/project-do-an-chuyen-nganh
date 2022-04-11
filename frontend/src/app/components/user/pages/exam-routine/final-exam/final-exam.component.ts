import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-final-exam',
  templateUrl: './final-exam.component.html',
  styleUrls: ['./final-exam.component.scss']
})
export class FinalExamComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Final Exam");
  }

  ngOnInit(): void {
  
  }

}
