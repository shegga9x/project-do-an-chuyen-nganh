import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services';
import { GeneralService } from 'src/app/services/general.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  account: any;

  constructor(private accountService: AccountService, private generalService: GeneralService) { }

  ngOnInit(): void {
    this.account = this.accountService.accountValue;
  }

  openDialogLogin() {
    console.log('open');
    this.generalService.openDialogLogin();
  }

  logout() {
    this.accountService.logout();
  }

}
