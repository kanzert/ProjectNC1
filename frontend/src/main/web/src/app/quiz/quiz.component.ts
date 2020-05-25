import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Question} from "../entities/question";
import {QuizService} from "../services/quiz.service";
import {ActivatedRoute} from "@angular/router";
import {QuestionService} from "../services/question.service";
import {SequenceOption} from "../entities/sequence-option";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {OptionService} from "../services/option.service";
import {Option} from "../entities/option";
import {Answer} from "../entities/answer";
import {UserService} from "../services/user.service";
import {UserSessionResult} from "../entities/UserSessionResult";
import {AchievementService} from "../services/achievement.service";
import {SessionStats} from "../entities/session-stats";
import {Notification} from "../entities/notification";
import {NotificationService} from "../services/notification.service";
declare var SockJS;
declare var Stomp;

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit, OnDestroy {
  private routeSub: Subscription;
  public stompClient;
  private serverUrl = 'http://localhost:8080/ws';
  questionOptionPoints = 0;
  quizId;
  sessionId;
  access_code='';
  finish=false;
  quizScore = 0;
  timeSpent=0;
  questions: Question[] = [];
  stats:SessionStats[]=[];
  userAnswers: Answer[] = [];
  optionalAnswers: Option[] = [];
  topStats: SessionStats[]=[];
  questionOptions = new Map();
  indexQuestion: number = 0;
  timer = 0;
  interval: any = null;
  timeout: any = null;
  optionType = 0;
  numberOfOptions = 4;
  optionsSequence: SequenceOption[] = Array.from({length: this.numberOfOptions},() =>
    ({
      serial_num: null,
      text: ''
    }));

  constructor(private quizService: QuizService,
              private optionService: OptionService,
              private route: ActivatedRoute,
              private questionService: QuestionService,
              private achievementService: AchievementService,
              private userService: UserService,
              private notficationService:NotificationService) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.quizId = params['id'];
      this.sessionId = params['sessionId'];
      this.getAccessCode();
      this.initializeWebSocketConnection();
    });
    this.getQuestions();
    console.log("join"+this.getUserJoin());

  }

  nextQuestion(clear: boolean): void {
    if(clear){
      clearInterval(this.interval);
      clearTimeout(+this.timeout);
    }
    if (this.indexQuestion < this.questions.length - 1) {
      this.indexQuestion++;
      this.startQuestionTimer();
    } else {
      this.interval = null;
    }
    this.optionSwitcher();
    if (this.indexQuestion === this.questions.length - 1) {
        this.finishSession();
    }
  }

  optionSwitcher() {
    switch (+this.questions[this.indexQuestion].type.id) {
      case 1:
        this.optionType = 1;
        break;
      case 2:
        this.optionType = 2;
        break;
      case 3: {
        this.optionType = 3;
        this.userAnswers.push({questionId: this.questions[this.indexQuestion].id , points: 0});
        this.optionalAnswers = this.questionOptions.get(this.questions[this.indexQuestion].id) as Option[];
        break;
      }
      case 4:
        this.optionType = 4;
        this.optionsSequence = this.questionOptions.get(this.questions[this.indexQuestion].id);
        break;
      default:
        this.optionType = 0;
    }
  }

  startQuestionTimer()  {
    this.optionSwitcher();
    this.timer = this.questions[this.indexQuestion].time;
    this.interval = setInterval(() => this.timer--, 1000);
    this.timeout = setTimeout(() => { clearInterval(this.interval);
        this.nextQuestion(false);},
      (this.questions[this.indexQuestion].time + 1) * 1000);
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
  defAnswer(answer: string) {
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id ,
      points: this.questionOptions.get(this.questions[this.indexQuestion].id)[0].answer === answer ? 10 : 0});
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.nextQuestion(true);
  }

  addPoint(point: number, event?) {
    const coef = 1 / this.optionalAnswers.filter(x => x.is_correct).length;
    point *= coef;
    this.questionOptionPoints += event.target.checked ? point : -point;
  }


  sendOptionAnswer() {
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id,
      points: this.questionOptionPoints*10
    });
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.nextQuestion(true);
  }


  seqAnswer() {
    let questionPoints = 0;

    for (let i = 0; i < this.optionsSequence.length; i++) {
      if (i + 1 === this.optionsSequence[i].serial_num) {
        questionPoints += 1/this.optionsSequence.length;
      }
    }
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id,
       points: questionPoints*10
    });
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.nextQuestion(true);
  }

  getQuestions() {
    this.questionService.getQuestions(this.quizId)
      .subscribe(questions => {
        this.questions = questions;
        this.optionType = +this.questions[0].type.id;

        for (let question of this.questions) {
          this.getOptions(question);
        }
      });
  }

  getOptions(question: Question) {
    if(question.type.name === 'a,b,c,d') {
      this.optionService.getOptions(question.id)
        .subscribe(options =>
        {this.questionOptions.set(question.id, options)
        }, error => console.error(error.message));
    }
    if(question.type.name === 'sequence') {
      this.optionService.getSequenceOptions(question.id)
        .subscribe(options =>
        {this.questionOptions.set(question.id, options)});
    }
    if(question.type.name === 'enter' || question.type.name === 'true/false') {
        this.optionService.getDefaultOptions(question.id)
          .subscribe(options =>
          {this.questionOptions.set(question.id, options)});
    }
  }



  getScore(): number {
    let score = 0;
    this.userAnswers.forEach(x => score += x.points);
    return score < 0 ? 0 : score;
  }


  drop(event: CdkDragDrop<string[]>) {moveItemInArray(this.optionsSequence, event.previousIndex, event.currentIndex);}

  getUserRole() {
    return this.userService.user.role.name;
  }

  getUserJoin(){
    return this.userService.user.joined;
  }

  startNewGame() {
    if (this.getUserRole() === 'user') {
      this.quizService.startSession(this.sessionId).subscribe(data =>
        console.log(data));
    }
  }

  getStats(){
    this.quizService.getStatsSession(this.sessionId).subscribe(
      data =>this.stats = data);
  }

  getTopStats(){
    this.quizService.getTopStats(this.quizId).subscribe(
      data=>   { this.topStats = data;});
  }
  getAccessCode(){
    this.quizService.getAccessCode(this.sessionId).subscribe(data =>
      this.access_code=data)
  }

  finishSession() {
    this.userService.user.joined=null;

    this.quizService.sendSessionStats({
      ses_id : this.sessionId,
      user_id : +this.userService.user.id,
      score : this.getScore(),
      time: this.timeSpent
    } as UserSessionResult).subscribe(data => {
      console.log(data);
      this.getTopStats();
      this.finish=true;
      console.log('Session finished, check achievements');
      this.achievementService.setUserAchievement().subscribe(data => {

        console.log(data);
        console.log('set achiv');
        this.finish=true;
      });

    });
    //this.getStats();

  }

  initializeWebSocketConnection() {
    this.notficationService.stompClient.subscribe('/start/'+this.sessionId, (message) => {
      if (message.body) {
        this.startQuestionTimer();
      }
    });

  }

  startGame() {
    this.notficationService.stompClient.send('/app/start/game' , {}, this.sessionId);
  }



}
