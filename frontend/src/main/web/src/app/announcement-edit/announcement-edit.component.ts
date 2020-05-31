import {Component, Input, OnInit} from '@angular/core';
import {Announcement} from '../entities/announcement';
import {UserService} from '../services/user.service';
import {AnnouncementService} from '../services/announcement.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {
  ANNOUNCEMENT_APPROVED,
  ANNOUNCEMENT_CREATED,
  ANNOUNCEMENT_DEFAULT_IMAGE,
  ANNOUNCEMENT_IMPORTANT
} from "../parameters";
import {UploadFilesService} from "../services/upload-files.service";

@Component({
  selector: 'app-announcement-edit',
  templateUrl: './announcement-edit.component.html',
  styleUrls: ['./announcement-edit.component.css']
})
export class AnnouncementEditComponent implements OnInit {

  @Input()
  editOnly = false;
  @Input()
  announcement: Announcement = {} as Announcement;
  error = '';
  message = '';
  imageUrl = ANNOUNCEMENT_DEFAULT_IMAGE;
  form: FormGroup;
  announcementForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router,
              private announcementService: AnnouncementService,
              private uploadFilesService: UploadFilesService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.announcementForm = this.fb.group({
      title: [this.announcement.title, [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      text: [this.announcement.text, [Validators.required, Validators.minLength(3), Validators.maxLength(499)]],
      important: [''],
      image: [this.announcement.image],
    });
  }

  onChanged(url:string){
    this.announcementForm.patchValue({
      image: url
    });
    this.announcement.image = url;
  }

  getUserRole(): string {
    return this.userService.user.role.name;
  }
  save() {
    this.cleanMessage();
    this.announcement.title = this.announcementForm.get('title').value;
    this.announcement.text = this.announcementForm.get('text').value;
    this.announcementService.updateAnnouncement(this.announcement).
    subscribe(resp => {this.message = 'Announcement updated!';},
      error => {this.error = error.message;});
  }

  add() {
    this.cleanMessage();
    this.announcement.title = this.announcementForm.get('title').value;
    this.announcement.text = this.announcementForm.get('text').value;
    this.announcement.userId = this.userService.user.id;
    this.announcement.statusId = this.selectStatus();
    this.announcementService.createAnnouncement(this.announcement).subscribe(resp => {
      this.message = 'Announcement added!';
    this.announcementForm.reset()},
      error => {this.error = error.message;});
  }
  delete() {
    this.cleanMessage();
    this.announcementService.deleteAnnouncement(+this.announcement.id).subscribe(
      resp => {this.message = 'Announcement delete!';
        this.router.navigateByUrl('/dashboard');
        this.announcementForm.reset()},
      error => {this.error = error.message;});
  }
  cleanMessage() {
    this.error = '';
    this.message = '';
  }
  selectStatus(): number {
    if (this.getUserRole() === 'user') {
      return ANNOUNCEMENT_CREATED;
    }
    if(this.announcementForm.get('important').value) {
      return ANNOUNCEMENT_IMPORTANT;
    } else {
      return ANNOUNCEMENT_APPROVED;
    }
  }
  submit() {
  }
}
