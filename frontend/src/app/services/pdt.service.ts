
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
const baseUrl = `${environment.apiUrl}/ptd_manager`;

@Injectable({
    providedIn: 'root'
})
export class PDTService {
    listSubAvailable: any[] = [];
    constructor(private http: HttpClient) { }

    addAccountFromExcel(listAccount: any) {
        return this.http.post(`${baseUrl}/add_Account_From_Excel`, listAccount);
    }
    deleteCourseOffering(ids: any) {
        return this.http.post(`${baseUrl}/delete_course_offering`, ids);
    }

    getCloseCourseRegist() {
        return this.http.get(`${baseUrl}/close_course_regist/`);
      }

      getCourseRegist() {
        return this.http.get(`${baseUrl}/get_course_regist/`);
      }

    addScoreFromExcel(listScoreRequest: any) {
        return this.http.post(`${baseUrl}/add_Score_From_Excel`, listScoreRequest);
    }

    loadEntity(entityClass: string): Promise<any> {
        return new Promise((resolve) => {
            let params = { entityClass: entityClass };
            this.http.get(`${baseUrl}/load_entity`, { params: params, }).subscribe(x => resolve(x));
        });
    }
    updateEntity(updateEntityRequest: any): Promise<any> {
        return new Promise((resolve, rejects) => {
            this.http.post(`${baseUrl}/update_entity`, updateEntityRequest).subscribe({
                next: (v) => resolve(v),
                error: (e) => rejects(e),
                complete: () => console.info('complete')
            })
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
