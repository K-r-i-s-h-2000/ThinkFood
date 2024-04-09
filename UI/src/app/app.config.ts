import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { HttpClientModule, provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { LoginService } from './login.service';
import { SignupService } from './signup.service';
import { loggerInterceptor } from './logger.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
// import { RestapiService } from './restapi.service';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule, provideAnimations} from '@angular/platform-browser/animations'
import { FaqService } from './service/faq.service';
import { ItemService } from './service/item.service';
import { CategoryService } from './service/category.service';
import { ReviewsAndRatingsService } from './service/reviews-and-ratings.service';


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(withFetch(), withInterceptors([loggerInterceptor])), LoginService, SignupService, BrowserModule, BrowserAnimationsModule, FaqService, ItemService, CategoryService, ReviewsAndRatingsService, provideAnimations()]};
