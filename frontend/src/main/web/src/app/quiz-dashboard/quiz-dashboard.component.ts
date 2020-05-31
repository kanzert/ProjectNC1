import {Component, OnInit} from '@angular/core';
import {Quiz} from '../entities/quiz';
import {QuizService} from '../services/quiz.service';
import {UserService} from "../services/user.service";
import {SessionStats} from "../entities/session-stats";

@Component({
  selector: 'app-quiz-dashboard',
  templateUrl: './quiz-dashboard.component.html',
  styleUrls: ['./quiz-dashboard.component.css']
})
export class QuizDashboardComponent implements OnInit {
  quizzes: Quiz[] = [];
  userQuizzes: Quiz[] = [];
  favoriteQuizzes: Quiz[] = [];
  suggestionQuizzes: Quiz[] = [];

  completedQuizes:Quiz[]=[];
  currentTab = 'Quizzes';

  constructor(private quizService: QuizService, private userService: UserService) { }

  ngOnInit(): void {
    if (this.getRole() === 'user') {
      this.getQuizzes();
      this.getUserQuizzes();
      this.getSuggestions();
      this.getCompletedQuizes();
    } else {
      this.getCreatedQuizzes();
    }
  }
  getRole() {
    return this.userService.user.role.name;
  }
  getCreatedQuizzes() {
    this.quizService.getCreatedQuizzes().
    subscribe(quizzes => this.quizzes = quizzes);
  }
  getQuizzes(): void {
    this.quizService.getQuizzes()
      .subscribe(quizzes => {
        this.quizzes = quizzes
      });
  }
  getCompletedQuizes():void{
    this.quizService.getCompletedQuizes()
      .subscribe(quizzes => this.completedQuizes = quizzes);
  }
  getUserQuizzes(): void {
    this.quizService.getUserQuizzes()
      .subscribe(quizzes => this.userQuizzes = quizzes);
  }

  getFavorite(): void {
    this.quizService.getFavoriteQuizzes()
      .subscribe(quizzes => this.favoriteQuizzes = quizzes);
  }

  getSuggestions(): void {
    this.quizService.getSuggestionsQuizzes()
      .subscribe(quizzes => {
        this.suggestionQuizzes = quizzes
      })
  }

  deleteQuiz(quiz: Quiz) {
    this.quizzes = this.quizzes.filter(q => q !== quiz);
    this.quizService.deleteQuiz(quiz);
  }

  quizTab(tab: string) {
      this.currentTab = tab;
      if (tab === 'Favorite') {
        this.getFavorite();
      }
  }
}
