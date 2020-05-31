import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserService} from "./user.service";
import {Observable, of, throwError} from "rxjs";
import {Announcement} from "../entities/announcement";
import {catchError, tap} from "rxjs/operators";
import {Notification} from '../entities/notification';
declare var SockJS;
declare var Stomp;
@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  // private apiUrl = 'http://localhost:8080/api/notification';
  // private serverUrl = 'http://localhost:8080/ws';
  private apiUrl = '/api/notification'
  private serverUrl = '/ws';

  constructor(private http: HttpClient, private userService: UserService) {
    if (userService.authenticated && userService.user.role.name === 'user') {
      this.initializeWebSocketConnection();
    }
  }

  notifications: Notification[] = [];

  public stompClient;
  initializeWebSocketConnection() {
    if (this.userService.user.role.name === 'user') {
      const ws = new SockJS(this.serverUrl);
      this.stompClient = Stomp.over(ws);
      const that = this;
      this.stompClient.connect({
        "Authorization": "Bearer_" + this.userService.getToken()
      }, function (frame) {
        that.getNotifications();
        that.stompClient.subscribe('/user/notification', (message) => {
          if (message.body) {
            that.notifications = JSON.parse(message.body);
          }
        });
      });
    }
  }
  disconnect() {
    if(this.stompClient) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
    this.notifications = [];
  }

  getNotifications() {
    this.stompClient.send('/app/get/notifications');
  }

  delete(notification: Notification[]) {
      this.stompClient.send('/app/delete/notifications' , {}, JSON.stringify(notification));
  }

  private handleError<T>(operation= 'operation') {
    return (error: any): Observable<T> => {
      return throwError(error);
    };
  }
  getRole() {
    return this.userService.user.role.name;
  }
}
