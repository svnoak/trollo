import { createSlice } from "@reduxjs/toolkit";
import {
  fetchAllWorkspaceLanes,
  renameLaneAsync,
  createLaneAsync,
  deleteLaneAsync,
  moveLaneAsync,
} from "../thunks/laneThunk";
import { createTaskAsync, deleteTaskAsync, fetchAllLaneTasksAsync, moveTaskAsync, updateTaskAsync } from "../thunks/taskThunk";

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
    createTask(state, action) {
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

    updateTask(state, action) {
      const { id, name, description, laneId } = action.payload;
      const task = state.lanes.find((lane) => lane.id === laneId)?.tasks.find((task) => task.id === id);
      if (task) {
        task.name = name;
        task.description = description;
      }
    },

    createLane(state, action) {
      state.lanes.push(action.payload);
    },

    deleteLane(state: { lanes: Lane[] }, action: { payload: number }) {
      const id = action.payload;
      state.lanes = state.lanes.filter(
        (lane: { id: number }) => lane.id !== id
      );
    },

    moveLane(state, action) {
      const { sourceIndex, destinationIndex } = action.payload;
      const lane = state.lanes.splice(sourceIndex, 1)[0];
      state.lanes.splice(destinationIndex, 0, lane);
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
    builder.addCase(fetchAllLaneTasksAsync.fulfilled, (state, action) => {
      console.log("FETCHING TASKS FROM THUN", action.payload);
      const lane = state.lanes.find((lane) => lane.id === action.payload.id);
      if (lane) {
        lane.tasks = action.payload.tasks;
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

    builder.addCase(updateTaskAsync.fulfilled, (state, action) => {
      const { id, name, description, laneId } = action.payload;
      const task = state.lanes.find((lane) => lane.id === laneId)?.tasks.find((task) => task.id === id);
      if (task) {
        task.name = name;
        task.description = description;
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
      if (state.lanes) {
        state.lanes = action.payload;
      }
    });

    builder.addCase(createLaneAsync.fulfilled, (state, action) => {
        state.lanes.push(action.payload);
    });

    builder.addCase(deleteLaneAsync.fulfilled, (state, action) => {
      const id: number = action.payload as unknown as number;
        state.lanes = state.lanes.filter(
          (lane: { id: number }) => lane.id !== id
        );
    });

    builder.addCase(moveLaneAsync.fulfilled, (state, action) => {
      const { sourceIndex, destinationIndex } = action.payload;
      const lane = state.lanes.splice(sourceIndex, 1)[0];
      state.lanes.splice(destinationIndex, 0, lane);
    });

    builder.addCase(createTaskAsync.fulfilled, (state, action) => {
      const { id, task } = action.payload;
      console.log("BUILDER", action.payload);
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.tasks.push(task);
      }
    });
  },
});

export const { createTask, deleteTask, moveTask, renameLane } = laneSlice.actions;
export default laneSlice.reducer;
