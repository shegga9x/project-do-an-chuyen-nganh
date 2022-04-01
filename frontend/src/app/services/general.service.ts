import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogLoginComponent } from '../user/shared/dialog-login/dialog-login.component';
@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(public dialog: MatDialog) { }

  openDialogLogin() {
    this.dialog.open(DialogLoginComponent, {
      // no data for this
      data: {},
    });
  }
}
