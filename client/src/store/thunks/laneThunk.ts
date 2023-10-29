import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const addTaskAsync = createAsyncThunk(
    "lane/addTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/lanes/" + task.laneId + "/tasks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: task.name }),
        });
        return await response.json();
    }
);

export const deleteTaskAsync = createAsyncThunk(
    "lane/deleteTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/lanes/" + task.laneId + "/tasks/" + task.id, {
            method: "DELETE",
        });
        return await response.json();
    }
);

export const moveTaskAsync = createAsyncThunk(
    "lane/moveTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/lanes/" + task.laneId + "/tasks/" + task.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ sourceLaneId: task.laneId, targetLaneId: task.laneId, newTaskPosition: task.position }),
        });
        return await response.json();
    }
);

export const renameLaneAsync = createAsyncThunk(
    "lane/renameLane",
    async (lane: Lane) => {
        const response = await fetch(baseUrl + "/api/lanes/" + lane.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: lane.name }),
        });
        return await response.json();
    }
);

export const fetchAllWorkspaceLanes = createAsyncThunk(
    "workspace/fetchAllWorkspaceLanes",
    async (workspaceId: number) => {
        const response = await fetch(baseUrl + "/api/workspaces/" + workspaceId + "/lanes");
        return await response.json();
    }
);