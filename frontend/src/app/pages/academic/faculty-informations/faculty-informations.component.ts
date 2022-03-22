import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-faculty-informations',
  templateUrl: './faculty-informations.component.html',
  styleUrls: ['./faculty-informations.component.scss']
})
export class FacultyInformationsComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Faculty Information");
  }

  ngOnInit(): void {
  }

}
