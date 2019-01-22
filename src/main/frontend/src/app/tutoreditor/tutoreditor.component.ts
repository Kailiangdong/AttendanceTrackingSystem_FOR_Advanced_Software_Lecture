import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Newattendance } from '../models/';
import { UserService } from '../services';

@Component({
  selector: 'app-tutoreditor',
  templateUrl: './tutoreditor.component.html',
  styleUrls: ['./tutoreditor.component.css']
})
export class TutoreditorComponent implements OnInit {
  attendanceForm: FormGroup;
  newattendance: Newattendance;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder) { 
      this.newattendance = {
        token: '',
        student_id: '',
        group: '',
        week: '',
        presented: 'true'
    };
  };

  ngOnInit() {
    this.initForm();
  }

  onSubmit() {
    // update license object with newattendance form values
    this.newattendance = { ...this.newattendance, ...this.attendanceForm.value };
    return this.create();
}
  
  goBack() {
    this.router.navigateByUrl('/tutorhome');
}
  private create() {
    this.userService.create(this.newattendance).subscribe(resp => {
      if(resp["status"] =="SUCCESS"){
        this.goBack();
      }
    });
  }

  private initForm() {
    this.attendanceForm = this.fb.group({
      token: [this.newattendance.token, Validators.required],
      student_id: [this.newattendance.student_id, Validators.required],
      group: [this.newattendance.group, Validators.required],
      week: [this.newattendance.week, Validators.required],
      presented: [this.newattendance.presented, Validators.required],
    });
  }
}
