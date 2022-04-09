import { CourseManageService } from 'src/app/services/course-manage.service';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as XLSX from 'xlsx';
import { GeneralService } from 'src/app/services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  result: any[];
  finish: boolean = false;

  constructor(
    private titleService: Title,
    private courseManageService: CourseManageService,
    private generalService: GeneralService
  ) {
    this.titleService.setTitle('Home');
  }

  ngOnInit(): void {

  }

  onFileChange(event: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    this.generalService.excelReader(target.files[0]).then(x => console.log(x))
  }
}

