import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserService} from "./user.service";
import {Observable, of} from "rxjs";
import {Question} from "../entities/question";
import {catchError} from "rxjs/operators";
import {Option} from "../entities/option";
import {DefaultOption} from "../entities/default-option";
import {SequenceOption} from "../entities/sequence-option";

@Injectable({
  providedIn: 'root'
})
export class OptionService {
  // private optionsUrl = 'http://localhost:8080/api';  // URL to web api
  private optionsUrl = '/api';  // URL to web api
  private readonly token: string;
  private httpHeader: HttpHeaders;
  constructor(private http: HttpClient, private userService: UserService) {
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  getOptions(question_id): Observable<Option[]> {
    return this.http.get<Option[]>(this.optionsUrl + `/options/${question_id}`,
      { headers: this.httpHeader })
      .pipe(
        catchError(this.handleError<Option[]>('getOptions', []))
      );
  }

  getDefaultOptions(question_id): Observable<DefaultOption[]> {
    return this.http.get<DefaultOption[]>(this.optionsUrl + `/default_options/${question_id}`,
      { headers: this.httpHeader })
      .pipe(
        catchError(this.handleError<DefaultOption[]>('getDefaultOptions', []))
      );
  }

  getSequenceOptions(question_id): Observable<SequenceOption[]> {
    return this.http.get<SequenceOption[]>(this.optionsUrl + `/sequence_options/${question_id}`,
      { headers: this.httpHeader})
      .pipe(
        catchError(this.handleError<SequenceOption[]>('getSequenceOptions', []))
      );
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


