import { createSlice } from "@reduxjs/toolkit";
import {
  addTaskAsync,
  deleteTaskAsync,
  fetchAllWorkspaceLanes,
  moveTaskAsync,
  renameLaneAsync,
} from "../thunks/laneThunk";

export type LaneState = {
    lanes: Lane[];
    lane: Lane | null;
    };

const laneSlice = createSlice({
  name: "lanes",
  initialState: {
    lanes: [] as Lane[],
    lane: null as Lane | null,
  },
  reducers: {
    addTask(state, action) {
      const { id, task } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.tasks.push(task);
      }
    },

    deleteTask(state, action) {
      const { laneId, taskId } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === laneId);
      if (lane) {
        lane.tasks = lane.tasks.filter(
          (task: { id: number }) => task.id !== taskId
        );
      }
    },

    moveTask(state, action) {
      const { sourceLaneId, destinationLaneId, sourceIndex, destinationIndex } =
        action.payload;
      const sourceLane = state.lanes.find((lane) => lane.id === sourceLaneId);
      const destinationLane = state.lanes.find(
        (lane) => lane.id === destinationLaneId
      );
      if (sourceLane && destinationLane) {
        const task = sourceLane.tasks.splice(sourceIndex, 1)[0];
        destinationLane.tasks.splice(destinationIndex, 0, task);
      }
    },

    renameLane(state, action) {
      const { id, name } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.name = name;
      }
    },
  },
  extraReducers: (builder) => {
    builder.addCase(addTaskAsync.fulfilled, (state, action) => {
      const { id, task } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.tasks.push(task);
      }
    });

    builder.addCase(deleteTaskAsync.fulfilled, (state, action) => {
      const { laneId, taskId } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === laneId);
      if (lane) {
        lane.tasks = lane.tasks.filter(
          (task: { id: number }) => task.id !== taskId
        );
      }
    });

    builder.addCase(moveTaskAsync.fulfilled, (state, action) => {
      const { sourceLaneId, destinationLaneId, sourceIndex, destinationIndex } =
        action.payload;
      const sourceLane = state.lanes.find((lane) => lane.id === sourceLaneId);
      const destinationLane = state.lanes.find(
        (lane) => lane.id === destinationLaneId
      );
      if (sourceLane && destinationLane) {
        const task = sourceLane.tasks.splice(sourceIndex, 1)[0];
        destinationLane.tasks.splice(destinationIndex, 0, task);
      }
    });

    builder.addCase(renameLaneAsync.fulfilled, (state, action) => {
      const { id, name } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.name = name;
      }
    });

    builder.addCase(fetchAllWorkspaceLanes.fulfilled, (state, action) => {
      if(state.lanes) {
        state.lanes = action.payload;
      }
    });
  },
});

export const { addTask, deleteTask, moveTask, renameLane } = laneSlice.actions;
export default laneSlice.reducer;
