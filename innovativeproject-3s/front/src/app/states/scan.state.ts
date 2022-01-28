import {State, Action, StateContext, Selector} from '@ngxs/store';
import {Scan} from '../models/scan';
import {ScanService} from '../services/scan.service';
import {AddScan, DeleteScan, GetScans} from '../actions/scan.action';
import {tap} from 'rxjs/operators';

export class ScanStateModel {
    scans: Scan[];
}


@State<ScanStateModel>({
    name: 'scans',
    defaults: {
        scans: []
    }
})

export class ScanState {
    constructor(private scanService: ScanService) { }

    @Selector()
    static getScanList(state: ScanStateModel) {
        return state.scans;
    }

    @Action(GetScans)
    getScans({setState, getState}: StateContext<ScanStateModel>, {payload}: GetScans) {
        return this.scanService.fetchScans(payload.email).subscribe((result) => {
            const state = getState();
            setState({
                ...state,
                scans: result,
            });
        });
    }

    @Action(AddScan)
    addScan(ctx: StateContext<ScanStateModel>, {payload}: AddScan) {
        return this.scanService.addScan(payload).subscribe((result) => {
            // ctx.dispatch(new GetScans());
        });
    }

    @Action(DeleteScan)
    deleteScan(ctx: StateContext<ScanStateModel>, {id}: DeleteScan) {
        return this.scanService.deleteScan(id).subscribe(() => {
            // ctx.dispatch(new GetScans());
        });
    }
}
