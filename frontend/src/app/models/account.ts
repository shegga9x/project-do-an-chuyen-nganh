import { Role } from './role';

export class Account {
    idAccount: string;
    title: string;
    firstName: string;
    lastName: string;
    email: string;
    role: Role[];
    jwtToken?: string;
}