import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models';

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    // getAll() {
    //     return this.http.get<User[]>(`${config.apiUrl}/users`);
    // }

    // getById(id: number) {
    //     return this.http.get(`${config.apiUrl}/users/` + id);
    // }

    register(user: User) {
        return this.http.post(`https://my-first-project-222110.appspot.com/rest/register`, user);
    }
    qrcode() {
        return this.http.get(`https://my-first-project-222110.appspot.com/rest/attendance/get/json`);
    }

    // update(user: User) {
    //     return this.http.put(`${config.apiUrl}/users/` + user.id, user);
    // }

    // delete(id: number) {
    //     return this.http.delete(`${config.apiUrl}/users/` + id);
    // }
}