import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { GeneralService, PDTService } from 'src/app/services';
@Component({
  selector: 'app-jsc-exam-result',
  templateUrl: './jsc-exam-result.component.html',
  styleUrls: ['./jsc-exam-result.component.scss']
})
export class JscExamResultComponent implements OnInit {

  constructor(private titleService: Title, private generalService: GeneralService, private pdtService: PDTService) {
    this.titleService.setTitle("Jsc Exam Result");
  }

  ngOnInit(): void {
  }

  onFileSubmit(file: HTMLInputElement) {
    if (file.files != null) {
      if (file.files.length !== 1) {
        throw new Error('Cannot use multiple files');
      }
      let listFieldNameDefualt = ["firstName", "lastName", "faculty"];
      this.generalService.excelReader(file.files[0], listFieldNameDefualt).then(listAccount => {
        console.log(listAccount);
        this.pdtService.addAccountFromExcel(listAccount).subscribe({
          next: (x) => {
            console.log(x);
          },
          error: (error) => {
            console.log(error);
          }
        });
      });
    }
  }
}
