import { Component, OnInit ,Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UserService } from '../services';

export interface DialogData {
  animal: string;
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
export class StudenthomeComponent {
  
  // list of attendance that will be displayed in the table
  //attendances: Attendance[] = [];
  // list of columns that will be displayed in the table
  displayedColumns: string[] = [
      'firstName',
      'lastName',
      'week',
      'attendance'
  ];

  animal: string;
  name: string;
  constructor(public dialog: MatDialog,/*private attendanceService: AttendanceService*/) {}
  ngOnInit(): void {
    //this.attendanceService.getList().subscribe(resp => this.attendances = resp);
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px',
      data: {name: this.name, animal: this.animal}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.animal = result;
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
           console.log(resp)
       }
   )
      this.myAngularxQrCode = 'Your QR code data string';
    }
    
  onNoClick(): void {
    this.dialogRef.close();
  }

}