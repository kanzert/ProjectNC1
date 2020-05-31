import {Component, Input, OnInit} from '@angular/core';
import {UserAchievement} from "../entities/user-achievement";
import {formatPercent} from "@angular/common";
import {Achievement} from "../entities/achievement";
import {AnnouncementService} from "../services/announcement.service";
import {UserService} from "../services/user.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-achievements',
  templateUrl: './achievements.component.html',
  styleUrls: ['./achievements.component.css']
})
export class AchievementsComponent implements OnInit {
  @Input()
  userAchievement: UserAchievement;
  @Input()
  achievement : Achievement;
  role: string;
  urlIcon;
  percentStatus;
  played;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.role = this.getRole();
    if (this.role === 'user') {
      if (this.userAchievement.played >= this.userAchievement.quizAmount) {
        this.percentStatus = 100;
        this.played = this.userAchievement.quizAmount;
      } else {
        this.percentStatus = (this.userAchievement.played / this.userAchievement.quizAmount * 100);
        this.played = this.userAchievement.played;
      }
      this.urlIcon = 'url("' + this.userAchievement.image +'")';
    } else {
      this.urlIcon = 'url("' + this.achievement.icon +'")';
    }

  }

  getRole() {
    return this.userService.user.role.name;
  }

  boxStatus(): string {
    let achievementBoxStatus = 'achievement-box-status-';
    if (this.percentStatus <= 25) {
      achievementBoxStatus += '25';
    } else if (this.percentStatus <= 50) {
      achievementBoxStatus += '50';
    } else if (this.percentStatus <= 75) {
      achievementBoxStatus += '75';
    } else if (this.percentStatus <= 100) {
      achievementBoxStatus += '100';
    }
    return achievementBoxStatus;
  }
}
