
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
const baseUrl = `${environment.apiUrl}/ptd_manager`;

@Injectable({
    providedIn: 'root'
})
export class PDTService {

    constructor(private http: HttpClient) { }

    addAccountFromExcel(listAccount: any) {
        return this.http.post(`${baseUrl}/add_Account_From_Excel`, listAccount);
    }
    addScoreFromExcel(listScoreRequest: any) {
        return new Promise((resolve,rejects) => {
            this.http.post(`${baseUrl}/add_Score_From_Excel`, listScoreRequest).subscribe({
                next: (v) => resolve(v),
                error: (e) => rejects(e),
                complete: () => console.info('complete')
            })
        });
    }
    loadEntity(entityClass: string): Promise<any> {
        return new Promise((resolve) => {
            let params = { entityClass: entityClass };
            this.http.get(`${baseUrl}/load_entity`, { params: params, }).subscribe(x => resolve(x));
        });
    }
    updateEntity(updateEntityRequest: any): Promise<any> {
        return new Promise((resolve) => {
            this.http.post(`${baseUrl}/update_entity`, updateEntityRequest).subscribe(x => resolve(x));
        });
    }
    addEntity(addEntityRequest: any): Promise<any> {
        return new Promise((resolve) => {
            this.http.post(`${baseUrl}/add_entity`, addEntityRequest).subscribe(x => resolve(x));
        });
    }
    deleteEntity(deleteEntityRequest: any): Promise<any> {
        return new Promise((resolve) => {
            this.http.post(`${baseUrl}/delete_entity`, deleteEntityRequest).subscribe(x => resolve(x));
        });
    }
}
