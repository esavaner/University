import {State, Action, StateContext, Selector} from '@ngxs/store';
import {User} from '../models/user';
import {UserService} from '../services/user.service';
import {AddUser, DeleteUser, GetUsers, UpdateUser} from '../actions/user.action';
import {tap} from 'rxjs/operators';

export class UserStateModel {
    users: User[];
}


@State<UserStateModel>({
    name: 'users',
    defaults: {
        users: []
    }
})

export class UserState {
    constructor(private userService: UserService) { }

    @Selector()
    static getUserList(state: UserStateModel) {
        return state.users;
    }

    @Action(GetUsers)
    getUsers({setState, getState}: StateContext<UserStateModel>, {payload}: GetUsers) {
        return this.userService.fetchUsers(payload.email).subscribe((result) => {
            const state = getState();
            setState({
                ...state,
                users: result
            });
        });
    }

    @Action(AddUser)
    addUser(ctx: StateContext<UserStateModel>, {payload}: AddUser) {
        return this.userService.addUser(payload).subscribe((result) => {
            //ctx.dispatch(new GetUsers());
        });
    }

    @Action(DeleteUser)
    deleteUser(ctx: StateContext<UserStateModel>, {id}: DeleteUser) {
        return this.userService.deleteUser(id).subscribe(() => {
            //ctx.dispatch(new GetUsers());
        });
    }

    @Action(UpdateUser)
    updateUser(ctx: StateContext<UserStateModel>, {payload, id}: UpdateUser) {
        return this.userService.updateUser(payload, id).subscribe((result) => {
            //ctx.dispatch(new GetUsers());
        });
    }
}
