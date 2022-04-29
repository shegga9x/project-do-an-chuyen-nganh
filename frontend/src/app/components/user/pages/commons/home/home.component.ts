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
    let reader: FileReader = new FileReader();
    reader.readAsBinaryString(target.files[0]);
    reader.onload = (e: any) => {
      /* create workbook */
      const binarystr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(binarystr, { type: 'binary' });
      /* selected the first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];
      /* save data */
      const resultFromExcel = XLSX.utils.sheet_to_json(ws);
      const resultFromExcelToString = JSON.stringify(resultFromExcel);
      const result = JSON.parse(resultFromExcelToString.toString());
      console.log(result);
      let resultString = "";
      let count = 0;
      result.forEach((element: any) => {
        // let id = element[Object.keys(element)[1]];
        // let firstName = element[Object.keys(element)[2]];
        // let lastName = element[Object.keys(element)[3]];
        // let clazzCode = element[Object.keys(element)[4]];
        // let commandAccount: string = `insert into ACCOUNT Values(N'${id}',N'${id}@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1);`;
        // let commandACCOUNT_has_role: string = `insert into ACCOUNT_has_role VALUES (N'${id}',3);`;
        // let commandACCOUNT_detail: string = `insert into ACCOUNT_detail VALUES (N'${id}','${firstName}','${lastName}','','','20/10/2018')`;
        // let commandVerification_Token: string = `insert into Verification_Token VALUES (N'${id}',null,getdate())`;
        // let commandStudent: string = `insert into Student Values(N'${id}',N'${firstName} ${lastName}','DT',getdate(),N'${clazzCode}',136,0)`;
        // resultString += commandAccount + "\n";

        let ID_Course = element[Object.keys(element)[1]];
        let ID_Course_Offering = count;
        let coureName = element[Object.keys(element)[2]];
        let Course_certificate = element[Object.keys(element)[3]];
        let years = element[Object.keys(element)[5]];
        let number_S = element[Object.keys(element)[6]];
        let optional = element[Object.keys(element)[7]];
        // ${id}

        var Teaching_Day = [2, 3, 4, 5, 6, 7];
        var period = [1, 4, 7, 10];
        var randPeriod = period[Math.floor(Math.random() * period.length)];
        var randPeriod2 = period[Math.floor(Math.random() * period.length)];
        let commandCourse: string = `insert into Course Values(N'${ID_Course}',null,N'${coureName}',${Course_certificate},${years},${number_S})`;
        let commandCourse_Offering: string = `insert into Course_Offering Values(N'${ID_Course_Offering}',N'${ID_Course}','DH18DTA',80,0)`;
        let commandSchedule = `insert into Schedule values(N'${count}',N''${ID_Course_Offering}',null,'LT',${Teaching_Day[Math.floor(Math.random() * Teaching_Day.length)]},'20/10/2021','20/11/2021',N'Rạng Đông',${randPeriod},${randPeriod + 3})` + "\n";
        if (Course_certificate == 4) {
          commandSchedule += `insert into Schedule values(N'${++count}',N''${ID_Course_Offering}',null,'TH',${Teaching_Day[Math.floor(Math.random() * Teaching_Day.length)]},'20/10/2021','20/11/2021',N'Rạng Đông',${randPeriod2},${randPeriod2 + 3})`;
        }

        count++
        // let ID_CourseA = element[Object.keys(element)[1]];
        // let ID_CourseB = element[Object.keys(element)[3]];
        // let commandFront_Sub: string = `insert into front_Sub values(N'${ID_CourseB}',N'${ID_CourseA}')`;


      });
      console.log(resultString)


    }
  }
}

