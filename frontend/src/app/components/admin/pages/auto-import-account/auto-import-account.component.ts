import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { GeneralService, PDTService } from 'src/app/services';

@Component({
  selector: 'app-auto-import-account',
  templateUrl: './auto-import-account.component.html',
  styleUrls: ['./auto-import-account.component.scss']
})
export class AutoImportAccountComponent implements OnInit {

  listError: any[] = [];
  listAll: any[] = [];
  listSuccess: any[] = [];
  finish: boolean = false;

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
      this.generalService.excelReader(file.files[0], listFieldNameDefualt).then((listAccount: []) => {

        this.listAll = listAccount;

        this.listError = listAccount.filter((x: any) => {
          return Object.keys(x).length != 3;
        })

        let listAccountAfterCheck = listAccount.filter((x: any) => {
          return Object.keys(x).length == 3;
        })

        this.pdtService.addAccountFromExcel(listAccountAfterCheck).subscribe({
          next: (x: any) => {
            Array.prototype.push.apply(this.listError, x);
            this.listSuccess = this.listAll.filter(item => {
              return !this.listError.find(ele => JSON.stringify(item) === JSON.stringify(ele));
            })
            this.finish = true;
          },
          error: (error) => {
            console.log(error);
            this.finish = true;
          }
        });
      });
    }
  }
}
