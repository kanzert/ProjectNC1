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
  @Output() onChanged = new EventEmitter<UserInvite>();
  imageUrl = 'https://img.icons8.com/plasticine/100/000000/user-male-circle.png';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  acceptUserInvite(): void {
    this.userService.acceptUserInvite(this.userInvite.id).subscribe(userInvites => {
      this.onChanged.emit(this.userInvite);
    });
  }

  declineUserInvite(): void {
    this.userService.declineUserInvite(this.userInvite.id).subscribe(userInvites => {
    });
  }
}
