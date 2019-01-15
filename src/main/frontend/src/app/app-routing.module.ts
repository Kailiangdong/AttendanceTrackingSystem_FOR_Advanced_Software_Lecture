import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { StudenthomeComponent } from './studenthome/studenthome.component';
import { TutorhomeComponent } from './tutorhome/tutorhome.component';
import { TutorlistComponent } from './tutorlist/tutorlist.component';
import { TutoreditorComponent } from './tutoreditor/tutoreditor.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'studenthome', component: StudenthomeComponent },
  { path: 'tutorhome', component: TutorhomeComponent },
  { path: 'tutorlist', component: TutorlistComponent },
  { path: 'tutoreditor', component: TutoreditorComponent }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}