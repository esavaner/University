export interface User {
    id: number;
    token: string;
    email: string;
    created: Date;
    // TODO: lista pozwolen
    permission: any[];
}
