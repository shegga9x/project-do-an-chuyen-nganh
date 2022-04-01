import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-mission-and-vision',
  templateUrl: './mission-and-vision.component.html',
  styleUrls: ['./mission-and-vision.component.scss']
})
export class MissionAndVisionComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Mission And Vission");
  }

  ngOnInit(): void {
  }

}
