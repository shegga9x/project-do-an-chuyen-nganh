import { Clazz, Course, CourseOffering, Faculty, Schedule } from 'src/app/models'
import { CourseManageService } from 'src/app/services/course-manage.service';
import { Component, OnInit, Inject } from '@angular/core';
import { L10N_LOCALE, L10nLocale } from 'angular-l10n';
import { Title } from "@angular/platform-browser";

@Component({  
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  result: any[];
  finish: boolean = false;

  constructor(
    @Inject(L10N_LOCALE) public locale: L10nLocale,
    private titleService: Title, private courseManageService: CourseManageService) {
    this.titleService.setTitle("Home");
  }

  ngOnInit(): void {
    this.getSubAvailable();
  }

  getSubAvailable() {

  }
}
