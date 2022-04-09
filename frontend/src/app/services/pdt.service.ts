import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const baseUrl = `${environment.apiUrl}/ptd_manager`;

@Injectable({
    providedIn: 'root'
})
export class PDTService {

    constructor(private http: HttpClient) { }

    addAccountFromExcel(listAccount: any)  {
        return new Promise((resolve) => {
            this.http.post(`${baseUrl}/add_Account_From_Excel`, listAccount).subscribe(x => resolve(x));
        });
    }
}
