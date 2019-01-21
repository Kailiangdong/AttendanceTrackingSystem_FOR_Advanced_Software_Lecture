import { Component, OnInit ,Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UserService } from '../services';
import { Attendance } from '../models';
export interface DialogData {
  name: string;
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

  name: string;
  constructor(public dialog: MatDialog,private userService: UserService) {}
  ngOnInit(): void {
    console.log("print it ")
    this.userService.getListFromStudent().subscribe(
      resp => {
        console.log(resp)
        this.attendances = resp['attandance_log']
        console.log(resp['attandance_log'])
      })
    }
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px',
      data: {name: this.name}
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
           this.myAngularxQrCode = JSON.stringify(resp)
          }
        )
    }
  onNoClick(): void {
    this.dialogRef.close();
  }

}