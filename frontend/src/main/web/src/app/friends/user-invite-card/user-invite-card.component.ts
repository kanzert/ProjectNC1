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
  @Output() onChanged = new EventEmitter<Boolean>();

  imageUrl = 'https://img.icons8.com/plasticine/100/000000/user-male-circle.png';
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
}
