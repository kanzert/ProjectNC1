import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "./user.service";
import {Observable, of, throwError} from "rxjs";
import {Notification} from '../entities/notification';
declare var SockJS;
declare var Stomp;

@Injectable({
  providedIn: 'root'
})
export class NotificationService {


  private serverUrl = 'http://localhost:8080/ws';
  notifications: Notification[] = [];
 public stompClient;
  
  constructor(private http: HttpClient, private userService: UserService) {
    if (userService.authenticated && userService.user.role.name === 'user') {
      this.initializeWebSocketConnection();
    }
  }
 
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
    this.stompClient.send('/app/get/notifications' , {}, this.userService.user.id);
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
