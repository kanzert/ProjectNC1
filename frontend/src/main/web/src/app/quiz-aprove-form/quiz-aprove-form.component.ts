import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {QuizService} from "../services/quiz.service";
import {Quiz} from "../entities/quiz";
import {Router} from "@angular/router";

@Component({
  selector: 'app-quiz-aprove-form',
  templateUrl: './quiz-aprove-form.component.html',
  styleUrls: ['./quiz-aprove-form.component.css']
})
export class QuizAproveFormComponent implements OnInit {

  @Input()
  quizId: number;
  approveForm: FormGroup;

  message = '';
  error = '';
  constructor(private fb: FormBuilder, private quizService: QuizService, private router: Router) { }

  ngOnInit(): void {
    this.approveForm = this.fb.group({
      comment: ['', [Validators.required, Validators.minLength(2)]],
      approveStatus: ['', [Validators.required]]}
     );
  }
  submit() {
      this.quizService.approveQuiz({id: this.quizId, description: this.approveForm.get('comment').value,
        status: {name: this.approveForm.get('approveStatus').value}} as Quiz).
      subscribe(res => {this.router.navigateByUrl('/dashboard')}
      , error => this.error = error.error.message);
  }
}
