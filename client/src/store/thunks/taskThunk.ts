import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const fetchTasksAsync = createAsyncThunk(
    "task/fetchTasks",
    async (laneId: number) => {
        const response = await fetch(baseUrl + "/api/lanes/" + laneId + "/tasks");
        return await response.json();
    }
);

export const updateTaskAsync = createAsyncThunk(
    "task/updateTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: task.name, description: task.description }),
        });
        return await response.json();
    }
);

export const createTaskAsync = createAsyncThunk(
    "task/createTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks", {
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
    "task/deleteTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "DELETE",
        });
        return await response.json();
    }
);

export const moveTaskAsync = createAsyncThunk(
    "task/moveTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id + "/move", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ sourceLaneId: task.laneId, targetLaneId: task.laneId, newTaskPosition: task.position }),
        });
        return await response.json();
    }
);