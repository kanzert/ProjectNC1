import { TestBed } from '@angular/core/testing';

import { QuizPlayService } from './quiz-play.service';

describe('QuizPlayService', () => {
  let service: QuizPlayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuizPlayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
