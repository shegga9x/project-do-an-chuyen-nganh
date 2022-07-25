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

  // onFileChange(event: any) {

  //   /* wire up file reader */
  //   const target: DataTransfer = <DataTransfer>(event.target);
  //   if (target.files.length !== 1) {
  //     throw new Error('Cannot use multiple files');
  //   }
  //   let reader: FileReader = new FileReader();
  //   reader.readAsBinaryString(target.files[0]);
  //   reader.onload = (e: any) => {
  //     /* create workbook */
  //     const binarystr: string = e.target.result;
  //     const wb: XLSX.WorkBook = XLSX.read(binarystr, { type: 'binary' });
  //     /* selected the first sheet */
  //     const wsname: string = wb.SheetNames[0];
  //     const ws: XLSX.WorkSheet = wb.Sheets[wsname];
  //     /* save data */
  //     const resultFromExcel = XLSX.utils.sheet_to_json(ws);
  //     const resultFromExcelToString = JSON.stringify(resultFromExcel);
  //     const result = JSON.parse(resultFromExcelToString.toString());
  //     // console.log(result);
  //     let resultString = "";
  //     let count = 0;
  //     let currentSemester = "";
  //     let totalGradeWithCertificate: number = 0;
  //     let totalCertificate: number = 0;
  //     let totalGrade: number = 0;
  //     let totalCertificateAll: number = 0;
  //     let totalCertificateFail: number = 0;
  //     result.forEach((element: any) => {
  //       // let id = element[Object.keys(element)[1]];
  //       // let firstName = element[Object.keys(element)[2]];
  //       let ID_Course = element[Object.keys(element)[1]];
  //       let ID_Course_Offering = count;
  //       let coureName = element[Object.keys(element)[2]];
  //       let Course_certificate: number = element[Object.keys(element)[3]];
  //       let years = element[Object.keys(element)[5]] - 2017;
  //       let number_S = element[Object.keys(element)[6]];
  //       let optional = element[Object.keys(element)[7]];

  //       // ${id}

  //       var Teaching_Day = [2, 3, 4, 5, 6, 7];
  //       var period = [1, 4, 7, 10];
  //       var randPeriod = period[Math.floor(Math.random() * period.length)];
  //       var randPeriod2 = period[Math.floor(Math.random() * period.length)];
  //       let commandCourse: string = `insert into Course Values(N'${ID_Course}','DT',N'${coureName}',${Course_certificate},${years},${number_S});`;
  //       let commandCourse_Progress: string = `insert into Course_Progress Values('DT',N'${ID_Course}',${years},${optional != "0301" ? 1 : 0});`;

  //       let grade: number = Number((Math.random() * (10 - 3) + 3).toFixed(2));
  //       let grade4: number = Number((grade * 0.4).toFixed(2));
  //       let rate: string = "A"
  //       if (grade < 4) { rate = "F" }
  //       else if (grade < 5) { rate = "D" }
  //       else if (grade < 6.5) { rate = "C" }
  //       else if (grade < 8) { rate = "B" }
  //       if (currentSemester != `'${(years + 2017)}_${(number_S)}'`) {
  //         currentSemester = `'${(years + 2017)}_${(number_S)}'`;
  //         let gradeAVofS: number = (Number((totalGradeWithCertificate / totalCertificate).toFixed(2)));
  //         let commandSemester_Result: string = ` insert into Semester_Result values(${currentSemester}, N'18130005', ${gradeAVofS}, ${(Number((gradeAVofS * 0.4).toFixed(2)))}, ${totalCertificate - totalCertificateFail});`;
  //         totalGradeWithCertificate = 0;
  //         totalCertificate = 0;
  //         totalCertificateFail = 0;
  //         resultString += commandSemester_Result + "\n";
  //       }
  //       let commandSub_Pass: string = ` insert into Sub_Pass values(${currentSemester}, N'${ID_Course}', N'18130005', ${grade}, ${grade4}, N'${rate}');`;
  //       totalGradeWithCertificate += (grade < 4 ? 0 : grade) * Course_certificate;
  //       totalGrade += (grade < 4 ? 0 : grade) * Course_certificate;
  //       totalCertificateAll += Number(Course_certificate);
  //       totalCertificate += Number(Course_certificate);
  //       totalCertificateFail += Number(grade < 4 ? Course_certificate : 0) ;
  //       resultString += commandSub_Pass + "\n";

  //       count++
  //       let ID_CourseA = element[Object.keys(element)[1]];
  //       let ID_CourseB = element[Object.keys(element)[3]];
  //       let commandFront_Sub: string = `insert into front_Sub values(N'${ID_CourseB}',N'${ID_CourseA}')`;
  //     });
  //     let gradeAVofS: number = (Number((totalGradeWithCertificate / totalCertificate).toFixed(2)));
  //     let gradeAVofTotal: number = (Number((totalGrade / totalCertificateAll).toFixed(2)));
  //     let commandSemester_Result: string = ` insert into Semester_Result values(${currentSemester}, N'18130005', ${gradeAVofS}, ${(Number((gradeAVofS * 0.4).toFixed(2)))}, ${totalCertificate - totalCertificateFail});`;
  //     let commandFinal_Result: string = ` insert into Final_Result values( N'18130005', ${gradeAVofTotal}, ${(Number((gradeAVofTotal * 0.4).toFixed(2)))});`;

  //     console.log(resultString += commandSemester_Result + "\n" + commandFinal_Result + "\n");


  //   }
  // }
}

