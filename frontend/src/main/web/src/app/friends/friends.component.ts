import {Component, OnInit} from '@angular/core';
import {UserInvite} from "../entities/user-invite";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})

export class FriendsComponent implements OnInit {
  userInvites: UserInvite[] = [];
  friendsList: UserInvite[] = [];
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUserInvites();
    this.getFriends();
  }

  getUserInvites(): void {
    this.userService.getUserInvite().subscribe(userInvites => {
      this.userInvites = userInvites
      console.log('From Friends' + userInvites);
    })
  }

  getFriends(): void {
    this.userService.getFriendsList().subscribe(friendsList => {
      this.friendsList = friendsList;
    })
  }

  onChanged($event: UserInvite) {
    const index = this.userInvites.indexOf($event);
    if (index > -1) {
      this.userInvites.splice(index, 1);
    }
    this.getFriends();
  }
}
