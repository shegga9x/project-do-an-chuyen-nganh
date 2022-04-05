import { Pipe, PipeTransform } from '@angular/core';
import { L10nTranslationService } from 'angular-l10n';
import { TranslateService } from 'src/app/services';
@Pipe({ name: 'translate' })
export class TranslatePipe implements PipeTransform {

    constructor(private translation: L10nTranslationService, private translateService: TranslateService) {
        let lang = localStorage.getItem('lang');
        if (lang === null) {
            translateService.setLocale('vn');
        } else {
            translateService.setLocale(lang);
        }
    }

    transform(word: string) {
        return this.translation.translate(word);
    }

}