import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-faculty-member',
  templateUrl: './faculty-member.component.html',
  styleUrls: ['./faculty-member.component.scss']
})
export class FacultyMemberComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Faculty Member");
  }
  ngOnInit(): void {
  }

}
