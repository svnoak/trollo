import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const fetchTasksAsync = createAsyncThunk(
    "task/fetchTasks",
    async (laneId: number) => {
        const response = await fetch(baseUrl + "/api/lanes/" + laneId + "/tasks");
        return await response.json();
    }
);

export const renameTaskAsync = createAsyncThunk(
    "task/renameTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: task.name }),
        });
        return await response.json();
    }
);

export const updateTaskDescriptionAsync = createAsyncThunk(
    "task/updateTaskDescription",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ description: task.description }),
        });
        return await response.json();
    }
);