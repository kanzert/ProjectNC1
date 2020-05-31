import {Component, Input, OnInit} from '@angular/core';
import {UserInvite} from "../../entities/user-invite";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-friend-profile-card',
  templateUrl: './friend-profile-card.component.html',
  styleUrls: ['./friend-profile-card.component.css']
})
export class FriendProfileCardComponent implements OnInit {
  @Input() userInvite: UserInvite;
  imageUrl = 'https://img.icons8.com/plasticine/100/000000/user-male-circle.png';

  constructor(private userService: UserService) { }

  ngOnInit(): void {

  }

  deleteFriend(): void {
    this.userService.deleteFriendFromList(this.userInvite.userIdFrom).subscribe(response => {
      console.log(response);
    })
  }
}
