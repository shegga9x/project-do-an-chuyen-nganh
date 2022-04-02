import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-our-principal',
  templateUrl: './our-principal.component.html',
  styleUrls: ['./our-principal.component.scss']
})
export class OurPrincipalComponent implements OnInit {

  constructor(private titleService: Title) {
    this.titleService.setTitle("Our Principal");
  }

  ngOnInit(): void {
  }

}
