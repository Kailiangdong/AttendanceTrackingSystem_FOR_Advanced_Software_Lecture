import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
const tmp = {
    "status" : "SUCCESS",
    "id" : "1234567891011",
    "first_name" : "firstName",
    "last_name" : "lastName",
    "is_tutor" : "false",
    "reason" : ""
}
@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    login(email: string, password: string) {
        let text = "email="+email+"&password="+password
        return this.http.post(`/rest/login`, text)
    }
    logout() {
        return this.http.post(`/rest/logout`,null)
    }
}