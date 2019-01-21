import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Attendance } from '../models/attendance';
import { Newattendance } from '../models/';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    // getAll() {
    //     return this.http.get<User[]>(`${config.apiUrl}/users`);
    // }

    // getById(id: number) {
    //     return this.http.get(`${config.apiUrl}/users/` + id);
    // }

    qrcode() {
        return this.http.get(`/rest/attendance/get/json`);
    }
    getListFromTutor(group: string, week: string){
        let text = "group="+group+"&week="+week
        return this.http.post(`/rest/attendance/log`, text);
    }
    getListFromStudent(){
        return this.http.post(`/rest/attendance/log`, null);
    }
    create(newattendance: Newattendance) {
        return this.http.post(`rest/attendance/record/json`, newattendance);
    }
    // update(user: User) {
    //     return this.http.put(`${config.apiUrl}/users/` + user.id, user);
    // }

    // delete(id: number) {
    //     return this.http.delete(`${config.apiUrl}/users/` + id);
    // }
}