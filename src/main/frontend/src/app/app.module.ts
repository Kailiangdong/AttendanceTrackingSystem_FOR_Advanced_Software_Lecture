import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { StudenthomeComponent } from './studenthome/studenthome.component';
import { StudentlistComponent } from './studentlist/studentlist.component';
import { TutorhomeComponent } from './tutorhome/tutorhome.component';
import { TutorlistComponent } from './tutorlist/tutorlist.component';
import { TutoreditorComponent } from './tutoreditor/tutoreditor.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule, MatSelectModule,MatIconModule, MatToolbarModule, MatOptionModule} from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    StudenthomeComponent,
    StudentlistComponent,
    TutorhomeComponent,
    TutorlistComponent,
    TutoreditorComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatButtonModule,
    MatIconModule, 
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatToolbarModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
