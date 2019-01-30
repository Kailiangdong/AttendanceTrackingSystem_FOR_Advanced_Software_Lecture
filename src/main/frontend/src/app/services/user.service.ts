import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Attendance } from '../models/attendance';
import { Newattendance } from '../models/';
import { Filter} from '../models/';
import { QRCodeComponent } from 'angularx-qrcode';
const text = {"status":"SUCCESS","attandance_log":[{"student_id":"5634472569470976","first_name":"li","last_name":"niu","group":"1","week_num":"1"},{"student_id":"5636953047302144","first_name":"mic","last_name":"niu","group":"6","week_num":"12"},{"student_id":"5636953047302144","first_name":"mic","last_name":"niu","group":"6","week_num":"1"},{"student_id":"5634472569470976","first_name":"li","last_name":"niu","group":"1","week_num":"2"},{"student_id":"5630742793027584","first_name":"quit","last_name":"rest","group":"1","week_num":"1"},{"student_id":"5636953047302144","first_name":"mic","last_name":"niu","group":"6","week_num":"10"}]}

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