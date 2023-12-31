import { createSelector } from "@reduxjs/toolkit";
import { RootState } from "../configureStore";

export const selectTasksByLaneId = (laneId: number) =>
    createSelector(
        (state: RootState) => state.lane.lanes,
        (lanes) => {
            const lane = lanes.find((lane) => lane.id === laneId);
            return lane ? lane.tasks : [];
        }
    );

