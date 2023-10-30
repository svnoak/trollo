import { createSlice } from "@reduxjs/toolkit";
import {
  fetchAllWorkspaceLanes,
  renameLaneAsync,
  createLaneAsync,
  deleteLaneAsync,
  moveLaneAsync,
} from "../thunks/laneThunk";
import {
  createTaskAsync,
  deleteTaskAsync,
  fetchAllLaneTasksAsync,
  moveTaskAsync,
  updateTaskAsync,
} from "../thunks/taskThunk";

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
    setLanes(state, action) {
      state.lanes = action.payload;
    },
  },

  extraReducers: (builder) => {
    builder.addCase(fetchAllLaneTasksAsync.fulfilled, (state, action) => {
      const lane = state.lanes.find((lane) => lane.id === action.payload.id);
      if (lane) {
        lane.tasks = action.payload.tasks;
      }
    });

    builder.addCase(deleteTaskAsync.fulfilled, (state, action) => {
      const { laneId, id } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === laneId);
      if (lane) {
        lane.tasks = lane.tasks.filter(
          (task: { id: number }) => task.id !== id
        );
      }
    });

    builder.addCase(updateTaskAsync.fulfilled, (state, action) => {
      const { id, name, description, laneId } = action.payload;
      const task = state.lanes
        .find((lane) => lane.id === laneId)
        ?.tasks.find((task) => task.id === id);
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
      const id: number = action.payload;
      state.lanes = state.lanes.filter(
        (lane: { id: number }) => lane.id !== id
      );
    });

    builder.addCase(moveLaneAsync.fulfilled, (state, action) => {
      state.lanes = action.payload.lanes;
    });

    builder.addCase(createTaskAsync.fulfilled, (state, action) => {
      const { id, task } = action.payload;
      const lane = state.lanes.find((lane) => lane.id === id);
      if (lane) {
        lane.tasks.push(task);
      }
    });

    builder.addCase(moveTaskAsync.fulfilled, (state, action) => {
      const { sourceLaneId, destinationLaneId, taskId, destinationIndex } = action.payload;
      const sourceLane = state.lanes.find((lane) => lane.id === sourceLaneId) as Lane;
      const destinationLane = state.lanes.find((lane) => lane.id === destinationLaneId) as Lane;
      const task = sourceLane.tasks.find((task) => task.id === taskId) as Task;

      console.log(taskId);

      sourceLane.tasks = sourceLane.tasks.filter((task) => task.id !== taskId);
      console.log(sourceLane.tasks);
    
      destinationLane.tasks.splice(destinationIndex, 0, task);
    });
  },
});

export default laneSlice.reducer;
