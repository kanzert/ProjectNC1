import { Component, OnInit } from '@angular/core';
import {SettingsService} from "../services/settings.service";
import {UserService} from "../services/user.service";
import {ANNOUNCEMENT_NOTIFICATION, SYSTEM_NOTIFICATION, QUIZ_NOTIFICATION,QUIZ_PLAY_ACTIVITY, QUIZ_CREATE_ACTIVITY, ACHIEVEMENT_RECEIVE_ACTIVITY, ANNOUNCEMENT_CREATE_ACTIVITY , QUIZ_LIKE_ACTIVITY} from '../parameters';
@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {


  announcementCategoryId = ANNOUNCEMENT_NOTIFICATION;
  quizCategoryId = QUIZ_NOTIFICATION;
  systemCategoryId = SYSTEM_NOTIFICATION;
  quizPlayCatId=QUIZ_PLAY_ACTIVITY;
  quizCreateCatId=QUIZ_CREATE_ACTIVITY;
  annCreateCatId=ANNOUNCEMENT_CREATE_ACTIVITY;
  quizLikeCatId=QUIZ_LIKE_ACTIVITY;


  quizPlayOn=true;
  quizCreateOn=true;
  achRecOn=true;
  annCreateOn=true;
  quizLikeOn=true;

  systemNotificationsOn: boolean;
  announcementNotificationsOn: boolean;
  quizNotificationsOn: boolean;
  constructor(private settingsService: SettingsService, private userService: UserService) { }

  ngOnInit(): void {
    this.getNotificationSettings();
    this.getActivitiesSettings();
  }

  getNotificationSettings() {
    this.settingsService.getNotificationSettings(+this.userService.user.id).subscribe(
      res => this.checkNotification());
  }

  getActivitiesSettings() {
    this.settingsService.getActivitiesSettings(+this.userService.user.id).subscribe(
      res => this.checkActivity());
  }

  changeSetting(catId: number, event) {
    this.settingsService.setNotificationSetting(
      {categoryId: catId, userId: +this.userService.user.id, seen: event.target.checked}).
    subscribe();

  }
  changeActivitySetting(catId: number, event) {
    this.settingsService.setActivitySetting(
      {user_id: +this.userService.user.id, enabled: event.target.checked, cat_id: catId}).
    subscribe();

  }

  checkActivity(){
    this.settingsService.activitySettings.
    forEach(set=>{
      if (set.cat_id === QUIZ_PLAY_ACTIVITY) {
        this.quizPlayOn = set.enabled;
      } else {
        if (set.cat_id === QUIZ_CREATE_ACTIVITY) {
          this.quizCreateOn = set.enabled;
        } else {
          if (set.cat_id === QUIZ_LIKE_ACTIVITY) {
            this.quizLikeOn = set.enabled;
          }else{
            if (set.cat_id === ANNOUNCEMENT_CREATE_ACTIVITY) {
              this.annCreateOn = set.enabled;
            }else{
              if (set.cat_id === ACHIEVEMENT_RECEIVE_ACTIVITY) {
                this.achRecOn = set.enabled;
              }else{

              }
            }
          }
        }
      }
    });
  }
  checkNotification() {
    this.settingsService.notificationSettings.
    forEach(not => {
      if (not.categoryId === ANNOUNCEMENT_NOTIFICATION) {
        this.announcementNotificationsOn = not.seen;
      } else {
        if (not.categoryId === SYSTEM_NOTIFICATION) {
          this.systemNotificationsOn = not.seen;
        } else {
          if (not.categoryId === QUIZ_NOTIFICATION) {
            this.quizNotificationsOn = not.seen;
          }
        }
      }
    });
  }
  getUserRole() {
    return this.userService.user.role.name;
  }
}
