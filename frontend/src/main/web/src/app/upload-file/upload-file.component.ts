import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {UploadFilesService} from "../services/upload-files.service";
import {User} from "../entities/user";
import {FileValidator} from "../user-profile/_helpers/file-input.validator";

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  @Output() onChanged = new EventEmitter<string>();

  error = '';
  message = '';
  form: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private uploadFilesService: UploadFilesService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      image: new FormControl('', [FileValidator.validate, Validators.required])
    });
  }

  onFileChange(event) {
    let file;
    if (event.target.files.length > 0) {
      // @ts-ignore
      const fileName = document.getElementById("fileInput").files[0].name;
      const nextSibling = event.target.nextElementSibling;
      nextSibling.innerText = fileName;
      file = event.target.files[0];
    }
    this.form.get('image').setValue(file);
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.form.get('image').value);
    this.uploadFilesService.upload(formData).subscribe(
      (res: string) => {
        this.onChanged.emit(res);
      },
      error => {
        console.log(error.text);
      }
    );
    this.form.reset();
  }
}
