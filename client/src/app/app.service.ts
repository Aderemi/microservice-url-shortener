import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8090/';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable()
export class WebApiObservableService {
  headers: HttpHeaders;
  options: {};

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Accept': 'application/json' });
    this.options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
  }

  getService(url: string): Observable<any> {
    return this.http
      .get(url, this.options);

  }

  getServiceWithDynamicQueryTerm(url: string, key: string, val: string): Observable<any> {
    return this.http
      .get(url + "/?" + key + "=" + val, this.options);

  }

  // getServiceWithFixedQueryString(url: string, param: any): Observable<any> {
  //   this.options = new RequestOptions({ headers: this.headers, search: 'query=' + param });
  //   return this.http
  //     .get(url, this.options);
  // }

  // getServiceWithComplexObjectAsQueryString(url: string, param: any): Observable<any> {
  //   let params: URLSearchParams = new URLSearchParams();
  //   for (var key in param) {
  //     if (param.hasOwnProperty(key)) {
  //       let val = param[key];
  //       params.set(key, val);
  //     }
  //   }
  //   this.options = new RequestOptions({ headers: this.headers, search: params });
  //   return this.http
  //     .get(url, this.options);
  // }

  createUrl(param: any): Observable<any> {
    let body = JSON.stringify(param);
    return this.http
      .post(API_URL + 'shorten', body, this.options);

  }

  getUrls(): Observable<any> {
    return this.http
      .get(API_URL + 'my-urls', this.options);
  }

  login(userName: string, password: string): Observable<any> {
    return this.http.post(API_URL + 'authenticate', {
      userName,
      password
    }, this.options);
  }

  register(userName: string, password: string): Observable<any> {
    return this.http.post(API_URL + 'signup', {
      userName,
      password
    }, this.options);
  }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }
//
//
//   updateService(url: string, param: any): Observable<any> {
//     let body = JSON.stringify(param);
//     return this.http
//       .put(url, body, this.options);
//   }
//
//   patchService(url: string, param: any): Observable<any> {
//     let body = JSON.stringify(param);
//     return this.http
//       .patch(url, body, this.options);
//   }
//
//   deleteService(url: string, param: any): Observable<any> {
//     let params: URLSearchParams = new URLSearchParams();
//     for (var key in param) {
//       if (param.hasOwnProperty(key)) {
//         let val = param[key];
//         params.set(key, val);
//       }
//     }
//     this.options = new RequestOptions({ headers: this.headers, search: params });
//     return this.http
//       .delete(url, this.options);
//   }
//
//   deleteServiceWithId(url: string, key: string, val: string): Observable<any> {
//     return this.http
//       .delete(url + "/?" + key + "=" + val, this.options);
//   }
//
//   private handleError(error: any) {
//     let errMsg = (error.message) ? error.message :
//       error.status ? `${error.status} - ${error.statusText}` : 'Server error';
//     console.error(errMsg);
//     throw new Error(errMsg)
// //    return Observable.throw(errMsg);
//   }
}
