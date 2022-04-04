import { map } from 'rxjs/operators';
import { AccountService } from './account.service';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

const baseUrl = `${environment.apiUrl}/course-manage`;

@Injectable({
    providedIn: 'root'
})
export class CourseManageService {

    constructor(private http: HttpClient, private service: AccountService) {
    }

    getSubAvailableRegist() {
        let params = { "id": this.service.accountValue?.idAccount + "" };
        return this.http.get(`${baseUrl}/get_sub_available_st/`, { params: params }).pipe(map((x: any) => { return x }));
    }
}