import { Tool } from '../models/tool';

export class AddTool {
    static readonly type = '[Tool] Add';

    constructor(public payload: Tool) {

    }
}

export class GetTools {
    static readonly type = '[Tool] Get';

    constructor(public payload: { email: string }) {}
}

export class DeleteTool {
    static readonly type = '[Tool] Delete';

    constructor(public id: number) {

    }
}
