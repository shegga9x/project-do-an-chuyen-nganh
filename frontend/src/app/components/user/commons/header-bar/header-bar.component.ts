import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.scss']
})
export class HeaderBarComponent implements OnInit {
  account: any;
  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.account = this.accountService.accountValue;
  }

}
