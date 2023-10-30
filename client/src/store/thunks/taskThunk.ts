import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const fetchAllLaneTasksAsync = createAsyncThunk(
    "task/fetchTasks",
    async (laneId: number) => {
        const response = await fetch(baseUrl + "/api/lanes/" + laneId + "/tasks");
        const json = await response.json();
        const payload = { id: laneId, tasks: json };
        return payload;
    }
);

export const updateTaskAsync = createAsyncThunk(
    "task/updateTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: task.name, description: task.description }),
        });
        const json = await response.json();
        console.log(json);
        return json;
    }
);

export const createTaskAsync = createAsyncThunk(
    "task/createTask",
    async (laneId: number) => {
        const response = await fetch(baseUrl + "/api/tasks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ laneId, name: "New Task" }),
        });
        const json = await response.json();
        const payload = { id: laneId, task: json };
        return payload;
    }
);

export const deleteTaskAsync = createAsyncThunk(
    "task/deleteTask",
    async (task: Task) => {
        const response = await fetch(baseUrl + "/api/tasks/" + task.id, {
            method: "DELETE",
        });
        if(!response.ok) {
            throw new Error("Error deleting task");
        }
        return task;
    }
);

export const moveTaskAsync = createAsyncThunk(
    "task/moveTask",
    async ({taskId, sourceLaneId, destinationLaneId, sourceIndex, destinationIndex}: {taskId: number, sourceLaneId: number, destinationLaneId: number, sourceIndex: number, destinationIndex: number}) => {
        console.log("THUNK");
        const response = await fetch(baseUrl + "/api/tasks/" + taskId + "/move", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ sourceLaneId, destinationLaneId, newTaskPosition: destinationIndex }),
        });
        console.log(response);
        if(!response.ok) {
            throw new Error("Error moving task");
        }
        return { taskId, sourceLaneId, destinationLaneId, sourceIndex, destinationIndex};
    }
);