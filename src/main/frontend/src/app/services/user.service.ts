import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Attendance } from '../models/attendance';
//import { User } from '../models';
const attendance = {
	"status" : "SUCCESS",
	"attendance_log" : [
        {
            "student_id" : "1234",
            "first_name" : "kailiang",
            "last_name" : "dong",
            "group" : "6",
            "week_num" : "2",
        },{
            "student_id" : "1234",
            "first_name" : "kailiang",
            "last_name" : "dong",
            "group" : "6",
            "week_num" : "3",
        },{
            "student_id" : "1234",
            "first_name" : "kailiang",
            "last_name" : "dong",
            "group" : "6",
            "week_num" : "6",
        },{
            "student_id" : "1234",
            "first_name" : "kailiang",
            "last_name" : "dong",
            "group" : "6",
            "week_num" : "9",
        },{
            "student_id" : "1234",
            "first_name" : "kailiang",
            "last_name" : "dong",
            "group" : "6",
            "week_num" : "12",
        }        
    ]
}

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
    getList(group: string, week: string){
        let text 
        if(group || week ){
            text = "group="+group+"&week="+week
        }else{
            text = null
        }
        //return attendance
        return this.http.post(`/rest/attendance/log`, text);
    }
    // update(user: User) {
    //     return this.http.put(`${config.apiUrl}/users/` + user.id, user);
    // }

    // delete(id: number) {
    //     return this.http.delete(`${config.apiUrl}/users/` + id);
    // }
}