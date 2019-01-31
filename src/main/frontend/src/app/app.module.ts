import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { StudenthomeComponent,DialogOverviewExampleDialog } from './studenthome/studenthome.component';
import { TutorhomeComponent } from './tutorhome/tutorhome.component';
import { TutoreditorComponent } from './tutoreditor/tutoreditor.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTableModule,MatCardModule,MatButtonModule, MatRadioModule,MatSelectModule,MatIconModule, MatToolbarModule, MatOptionModule, MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { QRCodeModule } from 'angularx-qrcode';
import { AlertService, AuthenticationService, UserService } from './services';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    StudenthomeComponent,
    TutorhomeComponent,
    TutoreditorComponent,
    DialogOverviewExampleDialog
  ],
  entryComponents: [DialogOverviewExampleDialog],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule, 
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    MatOptionModule,
    MatToolbarModule,
    MatDialogModule,
    QRCodeModule,
    MatTableModule
  ],
  providers: [
    AlertService,
    AuthenticationService,
    UserService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
