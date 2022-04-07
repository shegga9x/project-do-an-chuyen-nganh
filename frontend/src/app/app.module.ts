import { NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { appInitializer, ErrorInterceptor, JwtInterceptor } from 'src/app/helpers';
import { AccountService } from 'src/app/services';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';

//capcha
import { NgxCaptchaModule } from 'ngx-captcha';

//config l10n(include multi languages)
import { L10nTranslationModule, L10nIntlModule } from 'angular-l10n';
import { l10nConfig } from './l10n-config';


//component
import { AppComponent } from './app.component';
import { UserDeclarations } from './components/user/user.declarations';

//pipe
import { TranslatePipe } from './pipe/translate-pipe';

@NgModule({
  declarations: [
    AppComponent,
    TranslatePipe,
    ...UserDeclarations
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    L10nTranslationModule.forRoot(l10nConfig),
    L10nIntlModule,
    NgxCaptchaModule
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, multi: true, deps: [AccountService] },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
