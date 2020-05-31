import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {QuizService} from "./quiz.service";
import {UserService} from "./user.service";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class QuizPlayService {

  constructor(private http: HttpClient,
              private quizService: QuizService,
              private userService: UserService) {}

  private quizzesUrl = 'api/quiz';

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
