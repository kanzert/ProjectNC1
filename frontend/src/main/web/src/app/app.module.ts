import { BrowserModule } from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthorizationComponent } from './authorization/authorization.component';
import {HttpClientModule, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import {RegistrationComponent} from './registration/registration.component';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS
} from '@angular/common/http';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserCardComponent } from './user-card/user-card.component';
import {UserProfileRoutingModule} from "./user-profile/user-profile-routing.module";
import { QuizCreateComponent } from './quiz-create/quiz-create.component';
import { QuizEditComponent } from './quiz-edit/quiz-edit.component';
import { QuizDashboardComponent } from './quiz-dashboard/quiz-dashboard.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AnnouncementComponent } from './announcement/announcement.component';
import { AnnouncementEditComponent } from './announcement-edit/announcement-edit.component';
import {Observable} from "rxjs";
import {UserService} from "./user.service";
import { QuizComponent } from './quiz/quiz.component';



// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     req = req.clone({
//       setHeaders: {
//         'Content-Type' : 'application/json; charset=utf-8',
//         'Accept'       : 'application/json',
//         'Authorization': `Bearer_${UserService.getToken()}`,
//       },
//     });
//
//     return next.handle(req);
//   }
// }


@NgModule({
  declarations: [
    AppComponent,
    AuthorizationComponent,
    HomeComponent,
    RegistrationComponent,
    DashboardComponent,
    UserProfileComponent,
    UserCardComponent,
    QuizCreateComponent,
    QuizEditComponent,
    QuizDashboardComponent,
    AnnouncementComponent,
    AnnouncementEditComponent,
    QuizComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    UserProfileRoutingModule,
    DragDropModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

