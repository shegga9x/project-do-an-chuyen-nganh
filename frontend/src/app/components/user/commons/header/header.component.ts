import { Component, OnInit } from '@angular/core';
import { AccountService, GeneralService, TranslateService } from 'src/app/services';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  account: any;

  constructor(private accountService: AccountService,
    private generalService: GeneralService,
    private translateService: TranslateService,
    private router: Router) { }

  ngOnInit(): void {
    this.account = this.accountService.accountValue;
  }

  openDialogLogin() {
    if (!this.account) {
      this.generalService.openDialogLogin();
    }
  }

  logout() {
    this.accountService.logout();
  }

  changeLanguage(language: string) {
    localStorage.setItem('lang', language);
    // this.translateService.setLocale(language);
    this.generalService.onRefresh(this.router.url);
  }

}
