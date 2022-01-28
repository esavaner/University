import {State, Action, StateContext, Selector} from '@ngxs/store';
import {Tool} from '../models/tool';
import {ToolService} from '../services/tool.service';
import {AddTool, DeleteTool, GetTools} from '../actions/tool.action';
import {tap} from 'rxjs/operators';

export class ToolStateModel {
    tools: Tool[];
}


@State<ToolStateModel>({
    name: 'tools',
    defaults: {
        tools: []
    }
})

export class ToolState {
    constructor(private toolService: ToolService) { }

    @Selector()
    static getToolList(state: ToolStateModel) {
        return state.tools;
    }

    @Action(GetTools)
    getTools({setState, getState}: StateContext<ToolStateModel>, {payload}: GetTools) {
        return this.toolService.fetchTools(payload.email).subscribe((result) => {
            const state = getState();
            setState({
                ...state,
                tools: result,
            });
        });
    }

    @Action(AddTool)
    addTool(ctx: StateContext<ToolStateModel>, {payload}: AddTool) {
        return this.toolService.addTool(payload).subscribe((result) => {
            //ctx.dispatch(new GetTools());
        });
    }

    @Action(DeleteTool)
    deleteTool(ctx: StateContext<ToolStateModel>, {id}: DeleteTool) {
        return this.toolService.deleteTool(id).subscribe(() => {
            //ctx.dispatch(new GetTools());
        });
    }
}
