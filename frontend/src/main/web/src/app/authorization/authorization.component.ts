import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { Validators } from '@angular/forms';
import {UserService} from '../services/user.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {


  error = '';

  userForm: FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(3)]]
  });

  constructor(private fb: FormBuilder,
              private app: UserService,
              private http: HttpClient,
              private router: Router,
              private notification: NotificationService) {}

  login() {
    this.error = '';
    this.app.login({username: this.userForm.get('username').value,
      password: this.userForm.get('password').value}).
    subscribe(
      res => {this.router.navigateByUrl('/dashboard');
              this.notification.initializeWebSocketConnection()},
      error => {this.error = error.error.message});
  }

  ngOnInit(): void {

  }
}
