import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, of} from "rxjs";
import {Quiz} from "../entities/quiz";
import {UserService} from "./user.service";
import {Session} from "../entities/session";
import {SessionStats} from "../entities/session-stats";
import {UserSessionResult} from "../entities/UserSessionResult";
import {User} from "../entities/user";

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private quizzesUrl = 'http://localhost:8080/api/quiz';  // URL to web api
  // private quizzesUrl = '/api/quiz';  // URL to web api
  private userId;
  private readonly token: string;
  private httpHeader: HttpHeaders;

  constructor(private http: HttpClient, private userService: UserService) {
    this.userId = this.userService.user.id;
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  getQuiz(quizId): Observable<Quiz> {
    return  this.http.get<Quiz>(this.quizzesUrl + '/' + quizId,
      { headers: this.httpHeader})
      .pipe(
        catchError(this.handleError<Quiz>('getQuiz')
        ));
  }

  getCreatedQuizzes(): Observable<Quiz[]> {
    return  this.http.get<Quiz[]>(this.quizzesUrl + '/created',
      { headers: this.httpHeader})
      .pipe(
        catchError(this.handleError<Quiz[]>('getCreatedQuizzes',[])
        ));

  }

  getSession(quizId: number): Observable<Session> {
    return this.http.get<Session>(this.quizzesUrl + `/play/${this.userId}/${quizId}`,
      { headers: this.httpHeader })
      .pipe(catchError(this.handleError<Session>('getSession')));
  }

  startSession(sessionId) {
    return this.http.post(this.quizzesUrl + `/start/${sessionId}`, sessionId,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError('startSession')));
  }


  joinSession(accessCode): Observable<Session> {
    return this.http.get<Session>(this.quizzesUrl + `/join/?user_id=${this.userId}&access_code=` + accessCode,
      { headers: new HttpHeaders()
        .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
      .pipe(
        catchError(this.handleError<any>('joinSession')
        ));
  }

  getAccessCode(sessionId): Observable<string> {
    return this.http.get<string>(this.quizzesUrl + `/access_code/${sessionId}`,{ headers: new HttpHeaders()
        .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
      .pipe(
        catchError(this.handleError<string>('getAccessCode')
        ));
  }

  getStatsSession(sessionId): Observable<SessionStats[]> {
    return this.http.get<SessionStats[]>(this.quizzesUrl + `/stats/${sessionId}`,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
      .pipe(catchError(this.handleError<SessionStats[]>('getStatsSession')));
  }

  getTopStats(quizId): Observable<SessionStats[]> {
    return this.http.get<SessionStats[]>(this.quizzesUrl + `/topstats/${quizId}`,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
      .pipe(catchError(this.handleError<SessionStats[]>('getTopStats')));
  }


  sendSessionStats(userSessionResult: UserSessionResult) {
    return this.http.post<UserSessionResult>(this.quizzesUrl + `/finish`, userSessionResult,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<UserSessionResult>('sendSessionStats')));
  }

  getQuizzes(): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.quizzesUrl + "/approved/" + this.userId,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz[]>('getQuizzes', [])));
  }

  getUserQuizzes(): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.quizzesUrl + "/user/" + this.userId,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz[]>('getUserQuizzes', [])));
  }

  getFavoriteQuizzes(): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.quizzesUrl + '/favorite/' + this.userId,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz[]>('getFavoriteQuizzes', [])));
  }


  addFavoriteQuiz(quizId) {
    return this.http.post(this.quizzesUrl + '/favorite/' + quizId + '/' + this.userId,'Add favorite quiz',
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz>('addFavoriteQuiz')));
  }

  deleteFavoriteQuiz(quizId) {
    return this.http.delete(this.quizzesUrl + '/favorite/' + quizId + '/' + this.userId,
      { headers: this.httpHeader })
      .pipe(catchError(this.handleError<Quiz>('deleteFavoriteQuiz')));
  }

  getSuggestionsQuizzes(): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.quizzesUrl + '/suggestion/' + this.userId,
      { headers: this.httpHeader })
      .pipe(
        catchError(this.handleError<Quiz[]>('getSuggestionsQuizzes', []))
      );
  }
  getCompletedQuizes():Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.quizzesUrl + '/completed/' + this.userId,
      { headers: this.httpHeader })
      .pipe(
        catchError(this.handleError<Quiz[]>('getCompletedQuizes', []))
      );
  }

  /** POST: add a new quiz to the server */

  approveQuiz(quiz: Quiz): Observable<any> {
    return  this.http.post<Quiz>(this.quizzesUrl + '/approve', quiz,
      { headers: this.httpHeader})

  }

  createQuiz(quiz: Quiz): Observable<Quiz> {
    return this.http.post<Quiz>(this.quizzesUrl, quiz, { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz>('createQuiz')));
  }

  updateQuiz(quiz: Quiz): Observable<Quiz> {
    console.log("Hello from updateQuiz");
    return this.http.post<Quiz>(this.quizzesUrl + "/update/",
      { id: quiz.id, title: quiz.title, description: quiz.description, image: quiz.image },
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Quiz>('updateQuiz')));
  }

  deleteQuiz(quiz: Quiz): Observable<Quiz> {
    return this.http.delete<Quiz>(this.quizzesUrl + '/' + quiz.id,
      {headers: this.httpHeader})
      .pipe(catchError(this.handleError<any>('deleteQuiz')));
  }

  searchQuizzes(term: string, cat: string[], dateFrom: string, dateTo: string, userName: string): Observable<Quiz[]> {
    return this.http.post<Quiz[]>(this.quizzesUrl + '/search', { title: term, categories: cat, dateFrom: dateFrom, dateTo: dateTo, user: userName }, { headers: new HttpHeaders()
        .set('Authorization',  `Bearer_${this.userService.getToken()}`)}).pipe(
      catchError(this.handleError<Quiz[]>('searchQuizzes', []))
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
