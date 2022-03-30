// import { AccountService } from '@app/_services';

export function appInitializer() {
    return () => new Promise(resolve => {
        // attempt to refresh token on app start up to auto authenticate
        // accountService.refreshToken()
        //     .subscribe(account => console.log(account))
        //     .add(resolve);
    });
}