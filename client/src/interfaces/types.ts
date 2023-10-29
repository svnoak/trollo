import { WorkspaceState } from '../store/slices/workspaceSlice';
import { LaneState } from '../store/slices/laneSlice';
import { TaskState } from '../store/slices/taskSlice';

export type RootState = {
  workspace: WorkspaceState;
  lane: LaneState;
  task: TaskState;
};