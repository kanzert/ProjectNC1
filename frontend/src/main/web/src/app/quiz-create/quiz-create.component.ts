import {Component, OnInit} from '@angular/core';
import {QuizService} from "../services/quiz.service";
import {Quiz} from "../entities/quiz";
import {CategoryService} from "../services/category.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from '@angular/common';
import {UserService} from "../services/user.service";
import {FormBuilder, Validators} from "@angular/forms";
import {Category} from "../entities/category";


@Component({
  selector: 'app-quiz-create',
  templateUrl: './quiz-create.component.html',
  styleUrls: ['./quiz-create.component.css']
})

export class QuizCreateComponent implements OnInit {
  quizForm = this.fb.group({
    'title': ['', [Validators.required, Validators.maxLength(20)]],
    'description': ['', [Validators.required, Validators.maxLength(30)]],
    'image':['', Validators.required],
    'categories': [[], Validators.required]
  })

  categoriesList: Category[] = [];

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private userService: UserService,
    private categoryService: CategoryService
  ) { }

  onChanged(url:string){
    this.quizForm.patchValue({
      image: url
    });
  }

  ngOnInit(): void {
    this.getCategories();
  }

  goBack(): void {
    this.location.back();
  }

  createQuiz() {
    let selectedCategoriesId = [];
    for (let category of this.quizForm.get('categories').value) {
      selectedCategoriesId.push(category.id);
    }

    console.log("quiz created");
    this.quizService.createQuiz({
      title: this.quizForm.get('title').value,
      description: this.quizForm.get('description').value,
      image: this.quizForm.get('image').value,
      user_id: this.userService.user.id,
      categories: selectedCategoriesId
    } as Quiz)
      .subscribe(data  => {
        this.router.navigate(['/quiz-edit/' + data.id ]);
      });
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(
      (data: Category[]) => {
        this.categoriesList = data;
      }
    )
  }
}
