import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserInvite} from "../../entities/user-invite";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-user-invite-card',
  templateUrl: './user-invite-card.component.html',
  styleUrls: ['./user-invite-card.component.css']
})
export class UserInviteCardComponent implements OnInit {
  @Input() userInvite: UserInvite;
  @Input() isFriendList: boolean;
  @Output() onChanged = new EventEmitter<Boolean>();

  nameButtonAccept = 'Accept';
  nameButtonDecline = 'Decline';
  clicked = false;
  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  acceptUserInvite(): void {
    this.userService.acceptUserInvite(this.userInvite.id).subscribe(userInvites => {
      console.log(userInvites);
      this.onChanged.emit(true);
      this.onInviteAction('Accepted');
    });
  }

  declineUserInvite(): void {
    this.userService.declineUserInvite(this.userInvite.id).subscribe(userInvites => {
      console.log(userInvites);
      this.onInviteAction('Declined');
    });
  }

  onInviteAction(action: string) {
    this.clicked = true;
    if (action === 'Accepted') {
      this.nameButtonAccept = action;
    } else {
      this.nameButtonDecline = action;
    }
  }

  deleteFriend(): void {
    this.userService.deleteFriendFromList(this.userInvite.userIdFrom).subscribe(response => {
      console.log(response);
    })
  }
}
