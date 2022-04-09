import { DialogLoginComponent } from './../components/user/shared/dialog-login/dialog-login.component';
import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import * as XLSX from 'xlsx';
import { Router } from '@angular/router';
import { PDTService } from './pdt.service';
@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(public dialog: MatDialog, private router: Router, private pDTService: PDTService) { }

  openDialogLogin() {
    this.dialog.open(DialogLoginComponent, {
      // no data for this
      data: {},
    });
  }
  // use on refresh the special url
  onRefresh(uri: string) {
    this.router.navigateByUrl('/dummy', { skipLocationChange: true }).then(() => this.router.navigate([uri]));
  }
  renameKey(obj: any, oldKey: any, newKey: string) {
    if (oldKey != newKey) {
      obj[newKey] = obj[oldKey];
      delete obj[oldKey];
    }

  }
  excelReader(file: File): Promise<any> {
    return new Promise((resolve) => {
      let reader: FileReader = new FileReader();
      reader.readAsBinaryString(file);
      reader.onload = (e: any) => {
        /* create workbook */
        const binarystr: string = e.target.result;
        const wb: XLSX.WorkBook = XLSX.read(binarystr, { type: 'binary' });
        /* selected the first sheet */
        const wsname: string = wb.SheetNames[0];
        const ws: XLSX.WorkSheet = wb.Sheets[wsname];
        /* save data */
        const resultFromExcel = XLSX.utils.sheet_to_json(ws); // to get 2d array pass 2nd parameter as object {header: 1}
        const resultFromExcelToString = JSON.stringify(resultFromExcel);
        const result = JSON.parse(resultFromExcelToString.toString());
        var listFieldNameDefualt = ["studentID", "firstName", "lastName", "finalResult"];
        var listFieldNameObjectExcel = Object.keys(result[0]);
        for (let i = 0; i < result.length; i++) {
          for (let i1 = 0; i1 < listFieldNameObjectExcel.length; i1++) {
            this.renameKey(result[i], listFieldNameObjectExcel[i1], listFieldNameDefualt[i1]);
          }
        }
        this.pDTService.addAccountFromExcel(result).then(x => { resolve(x) });
      }
    });
  }
}
