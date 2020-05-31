import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserService} from "./user.service";
import {Category} from "../entities/category";
import {catchError} from "rxjs/operators";
import {Observable, throwError} from "rxjs";
import {Achievement} from "../entities/achievement";
import {UserAchievement} from "../entities/user-achievement";
import {Quiz} from "../entities/quiz";

@Injectable({
  providedIn: 'root'
})
export class AchievementService {

  private achievementUrl = 'http://localhost:8080/api/achievement';
  // private achievementUrl = '/api/achievement';
  private readonly token;
  private httpHeader;

  constructor(private http: HttpClient, private userService: UserService) {
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  getAchievements(): Observable<Achievement[]> {
    return this.http.get<Achievement[]>(this.achievementUrl + '/all',
      { headers: this.httpHeader}).pipe(
      catchError(this.handleError<any>('getAchievements'))
    );
  }

  getUserAchievements(): Observable<UserAchievement[]> {
    return this.http.get<UserAchievement[]>(this.achievementUrl + `/${this.userService.user.id}`,
      { headers: this.httpHeader}).pipe(
      catchError(this.handleError<any>('getUserAchievements'))
    );
  }

  createAchievement(achievement: Achievement) {
    return this.http.post<Achievement>(this.achievementUrl + '/create', achievement,
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError<Achievement>('createAchievement')));
  }

  setUserAchievement() {
    return this.http.post(this.achievementUrl + '/set' + `/${this.userService.user.id}`, '',
      { headers: this.httpHeader})
      .pipe(catchError(this.handleError('setUserAchievement')));
  }


  private handleError<T>(operation= 'operation') {
    return (error: any): Observable<T> => {
      console.log(operation + ' ' + error);
      return throwError(error);
    };
  }
}
