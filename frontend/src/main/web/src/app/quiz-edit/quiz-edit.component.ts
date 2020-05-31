import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Quiz} from "../entities/quiz";
import {QuizService} from "../services/quiz.service";
import {Question} from "../entities/question";
import {QuestionService} from "../services/question.service";
import {Option} from "../entities/option";
import {DefaultOption} from "../entities/default-option"
import {Subscription} from "rxjs";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {SequenceOption} from "../entities/sequence-option";
import {FormBuilder, Validators, FormGroup} from "@angular/forms";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-quiz-edit',
  templateUrl: './quiz-edit.component.html',
  styleUrls: ['./quiz-edit.component.css']
})
export class QuizEditComponent implements OnInit, OnDestroy {
  quiz_id;
  questionForm = this.fb.group({
    'questionText': ['', Validators.required],
    'questionType': ['not selected', Validators.required],
    'questionTime': ['not selected', Validators.required]
  })
  quiz: Quiz;
  isQuizLoaded = false;
  questions: Question[] = [];
  titleEditor = 'Add a question';
  quizForm: FormGroup;
  numberOfOptions = 2;
  options: Option[] = Array.from({length: this.numberOfOptions},()=>
    ({
      is_correct: false,
      text: ''
    }))
  answerTrueFalse = 'False';
  answerTypeAnswer = '';
  optionsSequence: SequenceOption[] = Array.from({length: this.numberOfOptions},()=>
    ({
      serial_num: null,
      text: ''
    }))
  private routeSub: Subscription;
  defaultOption: DefaultOption = {answer: ''};


  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private questionService: QuestionService,
    private quizService: QuizService,
  ) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.quiz_id = params['id'];
    });
    this.getQuestions();
    this.quizService.getQuiz(this.quiz_id)
        .subscribe(quiz => {
        console.log(quiz);
        this.quiz = quiz;
        this.isQuizLoaded = true;
        console.log(this.quiz.title);
          this.quizForm = this.fb.group({
            'title': [this.quiz.title, [Validators.required, Validators.maxLength(20)]],
            'description': [this.quiz.description, [Validators.required, Validators.maxLength(30)]],
            'image':[this.quiz.image]
          });
          console.log(this.quizForm.get('title').value);
      }
    );
  }
  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  onChanged(url:string){
    this.quizForm.patchValue({
      image: url
    });
  }

  editQuiz() {
    this.quizService.updateQuiz({
      id: this.quiz.id,
      title: this.quizForm.get('title').value,
      description: this.quizForm.get('description').value,
      image: this.quizForm.get('image').value,
      user_id: this.quiz.user_id
    } as Quiz).subscribe(res => console.log(res));
    console.log("quiz edited");
  }

  goBack() {
    this.location.back();
  }

  showEdit() {
    this.titleEditor = 'Edit a question';
  }

  addOption(optionType: string){
    if (optionType === 'select') {
      const option: Option = {
        is_correct: false,
        text: ""
      }
      if (this.options.length < 4) {
        this.options.push(option);
      }
    } else {
      const option: SequenceOption = {
        serial_num: null,
        text: ""
      }
      if (this.optionsSequence.length < 4) {
        this.optionsSequence.push(option);
      }
    }

  }
  deleteOption(optionType: string){
    if (optionType === 'select') {
      if (this.options.length > 2) {
        this.options.pop();
      }
    } else {
      if (this.optionsSequence.length > 2) {
        this.optionsSequence.pop();
      }
    }
  }

  //CRUD
  getQuestions(): void {
    this.questionService.getQuestions(this.quiz_id)
      .subscribe(questions => {
        console.log(questions);
        this.questions = questions;
      }
    );
  }

  getQuestion(question_id): void {
    this.questionService.getQuestion(question_id)
      .subscribe(question => {
        console.log(question);
      })
  }

  determineTypeQuestion(): any[] {
    let someOption=[];
    switch (this.questionForm.get('questionType').value) {
      case '1':
        this.defaultOption.answer = this.answerTrueFalse;
        someOption.push(this.defaultOption);
        break;
      case '2':
        this.defaultOption.answer = this.answerTypeAnswer;
        someOption.push(this.defaultOption);
        break;
      case '3':
        someOption = this.options;
        break;
      case '4':
        for (let i = 0; i < this.optionsSequence.length; i++) {
          this.optionsSequence[i].serial_num = i + 1;
        }
        someOption = this.optionsSequence;
        break;
    }

    return someOption;
  }


  createQuestion() {
    let someOption = this.determineTypeQuestion();
    console.log("question created");
    let question: Question  = {
      id: null,
      time: this.questionForm.get('questionTime').value,
      options: someOption,
      type: {id: this.questionForm.get('questionType').value, name: ''},
      text: this.questionForm.get('questionText').value,
      max_points: null,
      quiz_id: this.quiz_id,
      image: ''
    };
    this.questionForm.reset({
      'questionType': 'not selected',
      'questionTime': 'not selected'}
    );
    this.questionService.createQuestion(question as Question)
      .subscribe(data  => {
        question.id = data.id;
        this.questions.push(question);
        //this.getQuestion(data.id);
      });
  }

  deleteQuestion(question: Question): void {
    this.questions = this.questions.filter(q => q !== question);
    this.questionService.deleteQuestion(question.id).subscribe();
  }

  isCorrectOption(option: Option) {
    option.is_correct = !option.is_correct;
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.optionsSequence, event.previousIndex, event.currentIndex);
  }
}
