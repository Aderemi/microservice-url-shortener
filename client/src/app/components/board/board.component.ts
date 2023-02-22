import { Component, OnInit } from '@angular/core';
import { WebApiObservableService } from '../../app.service';
import {NgxSpinnerService} from "ngx-spinner";
import {NotificationsService} from "angular2-notifications";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  content?: {realUrl: string, fakeUrl: string}[];
  originalUrl: string = "";
  shortenedUrl = '';
  isUrlShortened = false;
  isLoggedIn = false;

  constructor(private spinner: NgxSpinnerService, private urlService: WebApiObservableService, private notifier: NotificationsService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.urlService.getToken();
    this.urlService.getUrls().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  getShortenedUrl() {
    if (this.originalUrl == undefined || this.originalUrl.length < 1 || this.originalUrl == null) {
      this.notifier.error("Form Error", "No URL was entered. Please Check and try again.");
    } else {
      // this.spinner.show();
      this.urlService.
      createUrl({ "realUrl": this.originalUrl }).
      subscribe((response) => {
        this.shortenedUrl = JSON.parse(response._body).url;
        this.spinner.hide();
        this.notifier.success("Success", "Created successfully")
        window.location.reload();
        if(this.isUrlShortened == false){
          this.isUrlShortened = true;
        }
      }, (error) => {
        this.spinner.hide();
        this.notifier.error("Error", "Oops! Something went wrong, please try again")
        console.log(error);
      });
    }
  }
}
