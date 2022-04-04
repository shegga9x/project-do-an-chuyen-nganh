import { L10nConfig } from 'angular-l10n';

import { i18nAsset } from './i18n';

export const l10nConfig: L10nConfig = {
  format: 'language-region',
  providers: [{ name: 'app', asset: i18nAsset }],
  fallback: false,
  cache: true,
  keySeparator: '.',
  defaultLocale: {
    language: 'vn-VN'
  },
  schema: [
    {
      locale: {
        language: 'en-US'
      },
      dir: 'ltr',
      text: 'United States',
    },
    {
      locale: {
        language: 'vn-VN'
      },
      dir: 'ltr',
      text: 'VN',
    },
  ],
};
