import { Component, OnInit ,Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UserService } from '../services';
import { Attendance } from '../models';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

export interface DialogData {
  week: string;
}

/**
 * @title Dialog Overview
 */
@Component({
  selector: 'app-studenthome',
  templateUrl: './studenthome.component.html',
  styleUrls: ['./studenthome.component.css']
})
export class StudenthomeComponent implements OnInit{
  filterForm = new FormGroup({
    weekrefresh: new FormControl(''),
  });
  // list of attendance that will be displayed in the table
  attendances : Attendance[] = [];
  // list of users that will be displayed in the table
  first_name: string;
  last_name: string;
  group: string;
  week_num: string;
  // list of columns that will be displayed in the table
  displayedColumns: string[] = [
      'first_name',
      'last_name',
      'group',
      'week_num'
  ];
  constructor(public dialog: MatDialog,private userService: UserService) {}
  ngOnInit(): void {
    this.userService.getListFromStudent().subscribe(
      resp => {
        this.attendances = resp['attandance_log']
      })
    }
    
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px',
      data: {week: this.filterForm.value}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}

@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {
  public myAngularxQrCode: string = null;
  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
      this.userService.qrcode().subscribe(
        resp => {
           const object = resp['token']
           for(let i = 0; i < object.length; i++) {
             let obj = object[i];
             if (obj.week ==data.week['weekrefresh']){
               this.myAngularxQrCode = obj.token
             }
          }
        }
        )
    }
  onNoClick(): void {
    this.dialogRef.close();
  }

}