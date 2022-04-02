import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogLoginComponent } from '../user/shared/dialog-login/dialog-login.component';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(public dialog: MatDialog, private router: Router,) { }

  openDialogLogin() {
    this.dialog.open(DialogLoginComponent, {
      // no data for this
      data: {},
    });
  }
  

  // use on refresh the special url
  onRefresh(uri: string) {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() =>
      this.router.navigate([uri]));
  }
}
