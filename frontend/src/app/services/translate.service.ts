import { Injectable, Inject } from '@angular/core';
import {
  L10N_CONFIG,
  L10nConfig,
  L10N_LOCALE,
  L10nLocale,
  L10nTranslationService,
} from 'angular-l10n';
import { L10nSchema } from 'angular-l10n/lib/models/types';
@Injectable({
  providedIn: 'root'
})
export class TranslateService {

  schema: L10nSchema[];

  constructor(
    @Inject(L10N_LOCALE) public locale: L10nLocale,
    @Inject(L10N_CONFIG) private l10nConfig: L10nConfig,
    private translation: L10nTranslationService
  ) {
    this.schema = this.l10nConfig.schema;
  }

  setLocale(language: string): void {
    console.log('???')
    if (language == "en") {
      console.log(this.translation.getLocale());
      localStorage.setItem('lang', 'en');
      this.translation.setLocale(this.schema[0].locale);
    } else {
      console.log(this.translation.getLocale());
      localStorage.setItem('lang', 'vn');
      this.translation.setLocale(this.schema[1].locale);
    }
  }
}
