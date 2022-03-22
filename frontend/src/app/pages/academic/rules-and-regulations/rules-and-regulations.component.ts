import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-rules-and-regulations',
  templateUrl: './rules-and-regulations.component.html',
  styleUrls: ['./rules-and-regulations.component.scss']
})
export class RulesAndRegulationsComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Rules And Regulation");
  }

  ngOnInit(): void {
  }

}
