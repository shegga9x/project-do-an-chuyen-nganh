import { Pipe, PipeTransform } from '@angular/core';
import {
    L10nTranslationService,
} from 'angular-l10n';
@Pipe({ name: 'translate' })
export class TranslatePipe implements PipeTransform {

    constructor(private translation: L10nTranslationService) { }
    
    transform(word: string) {
        return this.translation.translate(word);
    }
}