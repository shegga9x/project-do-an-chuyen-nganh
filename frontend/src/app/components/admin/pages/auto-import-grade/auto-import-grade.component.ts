import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { GeneralService, PDTService } from 'src/app/services';

@Component({
  selector: 'app-auto-import-grade',
  templateUrl: './auto-import-grade.component.html',
  styleUrls: ['./auto-import-grade.component.scss']
})
export class AutoImportGradeComponent implements OnInit {
  listFull: any[] = [];
  listError: any[];
  listOk: any[];
  finish: boolean = true;
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
   
      this.pDTService.addScoreFromExcel(request).subscribe({
        next: (x2) => { },
        error: (err) => {
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
  checkTableAllHeight() {
    let x = document.getElementById('dataTable') as HTMLElement
    if (x != null && x.clientHeight >= 300) {
      return true;
    }
    return false;
  }
  checkTableSuccessHeight() {
    let x = document.getElementById('dataTable2') as HTMLElement
    if (x != null && x.clientHeight >= 300) {
      return true;
    }
    return false;
  }
  checkTableErrorHeight() {
    let x = document.getElementById('dataTable3') as HTMLElement
    if (x != null && x.clientHeight >= 300) {
      return true;
    }
    return false;
  }
}

