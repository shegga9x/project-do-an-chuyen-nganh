import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { GeneralService } from 'src/app/services';
import { PDTService } from 'src/app/services/pdt.service';

@Component({
  selector: 'app-ssc-exam-result',
  templateUrl: './ssc-exam-result.component.html',
  styleUrls: ['./ssc-exam-result.component.scss'],
  encapsulation: ViewEncapsulation.None

})
export class SscExamResultComponent implements OnInit {
  listFull: any[] = [];
  listError: any[];
  listOk: any[];
  finish: boolean = false;
  constructor(private titleService: Title, private generalService: GeneralService, private pDTService: PDTService) {
    this.titleService.setTitle("Ssc Exam Result");
  }

  ngOnInit(): void {
  }

  onFileChange(event: any) {
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    let listFieldNameDefualt = ["studentID", "firstName", "lastName", "finalResult"];
    this.generalService.excelReader(target.files[0], listFieldNameDefualt).then(x => {
      this.listFull = x;
      let request = {
        idCourseOffering: "52", idSemester: "2021_2", is4Max: false, subScoreModels: this.listFull
      }
      //   this.pDTService.addScoreFromExcel(request).subscribe(x2 => {
      //     this.finish = true;
      //   }).catch((err) => {
      //     console.log(err)
      //     let objectError = JSON.parse(err)
      //     this.listError = this.listFull.filter(obj => {
      //       return objectError[obj['studentID']] != undefined
      //     })
      //     this.listError.forEach(element => {
      //       element.mess = objectError[element['studentID']]
      //     });
      //     this.listOk = this.listFull.filter(obj => {
      //       return objectError[obj['studentID']] == undefined
      //     })
      //     this.finish = true;
      //     console.log(this.listFull)
      //   });
      // })
      this.pDTService.addAccountFromExcel(request).subscribe({
        next: (x2) => {},
        error: (err) => {
          console.log(err)
          let objectError = JSON.parse(err)
          this.listError = this.listFull.filter(obj => {
            return objectError[obj['studentID']] != undefined
          })
          this.listError.forEach(element => {
            element.mess = objectError[element['studentID']]
          });
          this.listOk = this.listFull.filter(obj => {
            return objectError[obj['studentID']] == undefined
          })
          this.finish = true;
          console.log(this.listFull)
        }
      })
    })
  }
}