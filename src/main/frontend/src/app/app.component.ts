import { Component } from '@angular/core';
import { AuthenticationService } from './services';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {}
  logout() {
    this.authenticationService.logout().subscribe(
      resp => {
         if(resp["status"] =="SUCCESS"){
            this.router.navigate(['/login']);
         }
      }
    )
  }
}