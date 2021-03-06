import { Component, OnInit } from '@angular/core';
import { UserService } from '../services';
import { Attendance } from '../models';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-tutorhome',
  templateUrl: './tutorhome.component.html',
  styleUrls: ['./tutorhome.component.css']
})
export class TutorhomeComponent implements OnInit {
  // list of attendance that will be displayed in the table
  attendances : Attendance[] = [];
  // list of users that will be displayed in the table\
  student_id: string;
  first_name: string;
  last_name: string;
  group: string;
  week_num: string;
  // list of columns that will be displayed in the table
  displayedColumns: string[] = [
      'student_id',
      'first_name',
      'last_name',
      'group',
      'week_num'
  ];
  filterForm = new FormGroup({
    grouprefresh: new FormControl('all'),
    weekrefresh: new FormControl('all'),
  });
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getListFromTutor(this.filterForm.value)
    .subscribe(
      resp => {
        console.log(resp)
        this.attendances = resp['attandance_log']
        console.log(resp['attandance_log'])
      })
    }
  onRefresh() {
    this.userService.getListFromTutor(this.filterForm.value)
    .subscribe(
      resp => {
        this.attendances = resp['attandance_log']
      })
    }
  }
