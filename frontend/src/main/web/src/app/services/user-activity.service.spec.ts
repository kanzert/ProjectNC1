import { TestBed } from '@angular/core/testing';

import {UserActivityService} from './user-activity.service';

describe('SettingsService', () => {
  let service: UserActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
