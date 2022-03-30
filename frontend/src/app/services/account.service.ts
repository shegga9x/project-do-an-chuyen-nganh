import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';

import { Account } from 'src/app/models'

const baseUrl = `${environment.apiUrl}/accounts`;

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private accountSubject: BehaviorSubject<Account | null>;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    this.accountSubject = new BehaviorSubject<Account | null>(null);
  }

  public get accountValue(): Account | null {
    return this.accountSubject.value;
  }

}
