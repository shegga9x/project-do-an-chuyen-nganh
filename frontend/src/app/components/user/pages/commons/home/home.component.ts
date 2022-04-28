import { CourseManageService } from 'src/app/services/course-manage.service';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as XLSX from 'xlsx';
import { AccountService, GeneralService } from 'src/app/services';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  result: any[];
  finish: boolean = false;

  constructor(
    private router: Router,
    private titleService: Title,
    private accountService: AccountService,
    private generalService: GeneralService,
    private route: ActivatedRoute
  ) {
    this.titleService.setTitle('Home');
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      console.log(params)
      let token = params['token'];
      this.accountService.loginWithJWT(token).subscribe(x => this.generalService.onRefresh("/"));
    });

  }

  onFileChange(event: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    let listFieldNameDefualt = ["studentID", "firstName", "lastName", "finalResult"];
    this.generalService.excelReader(target.files[0], listFieldNameDefualt).then(x => console.log(x))
  }
}

