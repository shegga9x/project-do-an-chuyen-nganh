import { AccountService } from './account.service';
import { Injectable } from "@angular/core";
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from "@angular/router";
import { Observable } from "rxjs";

export class Permissions {
  canActivate(accountService: AccountService, path: string): boolean {
    if (accountService.accountValue?.role.filter((str) => String(str).toLowerCase().includes(path)).length != 0)
      return true;
    return false;
  }
}
@Injectable()
export class AuthenticationGuard implements CanActivate {
  constructor(private permissions: Permissions, private accountService: AccountService, private router: Router) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.permissions.canActivate(this.accountService, route.url[0].path))
      return true;
    return this.router.navigateByUrl('/dummy', { skipLocationChange: true }).then(() => this.router.navigate(['user']));
  }
}
