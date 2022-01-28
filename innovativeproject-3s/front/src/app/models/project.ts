import { Scan } from './scan';
import { Tool } from './tool';
import { User } from './user';

export interface Project {
    id: number;
    name: string;
    scans: Scan[];
    tools: Tool[];
    users: User[];
    status: string;
}
