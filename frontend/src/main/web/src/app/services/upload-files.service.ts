import { Injectable } from '@angular/core';
import {HttpClient, HttpEventType, HttpHeaders} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {UserService} from "./user.service";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadFilesService {
  // private apiURL = "http://localhost:8080/api/storage";
  private apiURL = "/api/storage";
  private userId;
  private readonly token: string;
  private httpHeader: HttpHeaders;

  constructor(private httpClient: HttpClient, private userService: UserService) {
    this.userId = this.userService.user.id;
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  public upload(data) {
    let uploadURL = `${this.apiURL}/uploadFile`;
    return this.httpClient.post(uploadURL, data, {
      headers: this.httpHeader,
      responseType: 'text'
    }).pipe(catchError(this.handleError('upload')));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
