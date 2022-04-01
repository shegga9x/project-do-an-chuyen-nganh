import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  account: any;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.account = this.accountService.accountValue;
  }

  logout() {
    this.accountService.logout();
  }

}
