import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const baseUrl = `${environment.apiUrl}/accounts`;

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor() { }
}
