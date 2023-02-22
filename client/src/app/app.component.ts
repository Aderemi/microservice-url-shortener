import { Component } from '@angular/core';
import { WebApiObservableService } from './app.service';
import { NotificationsService } from 'angular2-notifications';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'app';
  public options = {
    Position: ['top', 'right'],
    showProgressBar : true,
    timeOut: 2000,
    lastOnBottom: true,
    clickToClose : true,
    preventDuplicates : true,
  };
  isLoggedIn = false;

  username?: string;

  constructor(private spinner: NgxSpinnerService, private urlService: WebApiObservableService, private notifier: NotificationsService) {

  }
  ngOnInit(): void {
    this.isLoggedIn = !!this.urlService.getToken();

    if (this.isLoggedIn) {
      const user = this.urlService.getUser();

      this.username = user.username;
    }
  }

  logout(): void {
    this.urlService.signOut();
    window.location.reload();
  }
}
