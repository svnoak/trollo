import { createAsyncThunk } from "@reduxjs/toolkit";

const baseUrl = "http://localhost:3000";

export const fetchWorkspaces = createAsyncThunk(
    "workspace/fetchWorkspaces",
    async () => {
        const response = await fetch(baseUrl + "/api/workspaces");
        return await response.json();
    }
);

export const deleteWorkspaceAsync = createAsyncThunk(
    "workspace/deleteWorkspace",
    async (id: number) => {
            const response = await fetch(baseUrl + "/api/workspaces/" + id, {
                method: "DELETE",
            });

            if(!response.ok) {
                throw new Error("Error deleting workspace");
            }

            return id;
    }
);

export const createWorkspaceAsync = createAsyncThunk(
    "workspace/createWorkspace",
    async () => {
        const response = await fetch(baseUrl + "/api/workspaces", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: "New Workspace"}),
        });
        return await response.json();
    }
);

export const updateWorkspaceNameAsync = createAsyncThunk(
    "workspace/updateWorkspaceName",
    async (workspace: {id: number, name: string}) => {
        console.log(workspace.id);
        const response = await fetch(baseUrl + "/api/workspaces/" + workspace.id + "/name", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name: workspace.name }),
        });
        if(!response.ok) {
            throw new Error("Error updating workspace name");
        }
        return await response.json();
    }
);