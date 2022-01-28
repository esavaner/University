import { Project } from '../models/project';

export class AddProject {
    static readonly type = '[Project] Add';

    constructor(public payload: Project, public email: string) {

    }
}

export class GetProjects {
    static readonly type = '[Project] Get';

    constructor(public payload: { email: string }) {}
}

export class DeleteProject {
    static readonly type = '[Project] Delete';

    constructor(public id: number, public email: string) {

    }
}

export class UpdateProject {
    static readonly type = '[Project] Update';

    constructor(public payload: Project, public id: number, public email: string) {

    }
}

export class SetSelectedProject {
    static readonly type = '[Project] Set';

    constructor(public payload: Project) {

    }
}
