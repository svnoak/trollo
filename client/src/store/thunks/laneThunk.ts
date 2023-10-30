import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const createLaneAsync = createAsyncThunk(
    "lane/createLane",
    async (workspaceId: number) => {
        const response = await fetch(baseUrl + "/api/lanes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ workspaceId: workspaceId, name: "New Lane" }),
        });
        if(!response.ok) {
            throw new Error("Error creating lane");
        }
        return await response.json();
    }
);

export const deleteLaneAsync = createAsyncThunk(
    "lane/deleteLane",
    async (laneId: number ) => {
        const response = await fetch(baseUrl + "/api/lanes" + laneId, {
            method: "DELETE",
        });
        return response;
    }
);

export const renameLaneAsync = createAsyncThunk(
    "lane/renameLane",
    async (lane: Lane) => {
        const response = await fetch(baseUrl + "/api/lanes/" + lane.id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: lane.name }),
        });
        return await response.json();
    }
);

export const moveLaneAsync = createAsyncThunk(
    "lane/moveLane",
    async ({lane, newPosition}: {lane: Lane, newPosition: number}) => {
        const response = await fetch(baseUrl + "/api/lanes/" + lane.id + "/move/" + newPosition, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return await response.json();
    }
);

export const fetchAllWorkspaceLanes = createAsyncThunk(
    "lane/fetchAllWorkspaceLanes",
    async (workspaceId: number) => {
        const response = await fetch(baseUrl + "/api/workspaces/" + workspaceId + "/lanes");
        return await response.json();
    }
);