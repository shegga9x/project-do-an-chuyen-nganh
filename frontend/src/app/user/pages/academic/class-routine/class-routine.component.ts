import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-class-routine',
  templateUrl: './class-routine.component.html',
  styleUrls: ['./class-routine.component.scss']
})
export class ClassRoutineComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Class Routine");
  }

  ngOnInit(): void {
  }

}
