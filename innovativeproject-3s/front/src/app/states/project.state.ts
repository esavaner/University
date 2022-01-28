import {State, Action, StateContext, Selector} from '@ngxs/store';
import {Project} from '../models/project';
import {ProjectService} from '../services/project.service';
import {AddProject, DeleteProject, GetProjects, UpdateProject, SetSelectedProject} from '../actions/project.action';
import { map } from 'rxjs/operators';

export class ProjectStateModel {
    projects: Project[];
    selected: Project;
}

@State<ProjectStateModel>({
    name: 'projects',
    defaults: {
        projects: [],
        selected: null,
    }
})

export class ProjectState {
    constructor(private projectService: ProjectService) { }

    @Selector()
    static getProjectList(state: ProjectStateModel) {
        return state.projects;
    }

    @Selector()
    static getSelectedProject(state: ProjectStateModel) {
        return state.selected;
    }

    @Action(GetProjects)
    getProjects({setState, getState}: StateContext<ProjectStateModel>, {payload}: GetProjects) {
        return this.projectService.fetchProjects(payload.email).subscribe((result) => {
            const state = getState();
            result.map(project => {
                for (const scan of project.scans) {
                    project.status = 'positive';
                    if (scan.status === 'waiting') {
                        project.status = 'waiting';
                        break;
                    } else if (scan.status === 'negative') {
                        project.status = 'waiting';
                        break;
                    }
                }
            });
            setState({
                ...state,
                projects: result,
            });
        });
    }

    @Action(AddProject)
    addProject(ctx: StateContext<ProjectStateModel>, {payload, email}: AddProject) {
        return this.projectService.addProject(payload).subscribe((result) => {
            ctx.dispatch(new GetProjects({email}));
        });
    }

    @Action(DeleteProject)
    deleteProject(ctx: StateContext<ProjectStateModel>, {id, email}: DeleteProject) {
        return this.projectService.deleteProject(id).subscribe(() => {
            ctx.dispatch(new GetProjects({email}));
        });
    }

    @Action(UpdateProject)
    updateProject(ctx: StateContext<ProjectStateModel>, {payload, id, email}: UpdateProject) {
        return this.projectService.updateProject(payload, id).subscribe((result) => {
            ctx.dispatch(new GetProjects({email}));
        });
    }

    @Action(SetSelectedProject)
    setSelectedProject({setState, getState}: StateContext<ProjectStateModel>, {payload}: SetSelectedProject) {
        const state = getState();
        setState( {
            ...state,
            selected: payload
        });
    }
}
