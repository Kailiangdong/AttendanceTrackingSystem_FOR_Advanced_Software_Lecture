import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Attendance } from '../models/attendance';
import { Newattendance } from '../models/';
import { Filter} from '../models/';
@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    qrcode() {
        return this.http.get(`/rest/attendance/get/json`);
    }
    getListFromTutor(filter:Filter){
        let text = "group="+filter.grouprefresh+"&week="+filter.weekrefresh;
        return this.http.post(`/rest/attendance/log`, text);
    }
    getListFromStudent(){
        return this.http.post(`/rest/attendance/log`, null);
    }
    create(newattendance: Newattendance) {
        let text = "token="+newattendance.token+"&student_id="+newattendance.student_id
        +"&group="+newattendance.group+"&week="+newattendance.week+"&presented="+newattendance.presented
        return this.http.post(`rest/attendance/record/json`, text);
    }
}