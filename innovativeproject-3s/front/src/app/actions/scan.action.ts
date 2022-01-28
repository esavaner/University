import { Scan } from '../models/scan';

export class AddScan {
    static readonly type = '[Scan] Add';

    constructor(public payload: Scan) {

    }
}

export class GetScans {
    static readonly type = '[Scan] Get';

    constructor(public payload: { email: string }) {}
}

export class DeleteScan {
    static readonly type = '[Scan] Delete';

    constructor(public id: number) {

    }
}
