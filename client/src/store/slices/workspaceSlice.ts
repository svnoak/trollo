import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { createWorkspaceAsync, deleteWorkspaceAsync, fetchWorkspaces, updateWorkspaceNameAsync } from "../thunks/workspaceThunk";

export type WorkspaceState = {
  workspacesArray: Workspace[];
  workspace: Workspace | undefined;
};

const workspaceSlice = createSlice({
  name: "workspace",
  initialState: {
    workspacesArray: [] as Workspace[],
    workspace: undefined as Workspace | undefined,
  },
  reducers: {
    deleteWorkspace(state, action) {
      const id = action.payload;
      state.workspacesArray = state.workspacesArray.filter(
        (workspace: { id: number }) => workspace.id !== id
      );
    },

    createWorkspace(state, action: PayloadAction<Workspace>) {
      state.workspacesArray.push(action.payload);
    },
    
    setActiveWorkspace(state, action) {
      if(!action.payload) {
        state.workspace = undefined;
      } else {
        const id = action.payload.id;
        state.workspace = state.workspacesArray.find(
          (workspace: { id: number }) => workspace.id === id
        );
      }
    },

  },
  extraReducers: (builder) => {
    builder.addCase(fetchWorkspaces.fulfilled, (state, action) => {
      state.workspacesArray = action.payload;
    });

    builder.addCase(deleteWorkspaceAsync.fulfilled, (state, action) => {
      const id = action.payload;
      state.workspacesArray = state.workspacesArray.filter(
        (workspace: { id: number }) => workspace.id !== id
      );
    })

    builder.addCase(createWorkspaceAsync.fulfilled, (state, action) => {
      state.workspacesArray.push(action.payload);
    });

    builder.addCase(updateWorkspaceNameAsync.fulfilled, (state, action) => {
      const { id, name } = action.payload;
      const workspace = state.workspacesArray.find(
        (workspace: { id: number }) => workspace.id === id
      );
      if (workspace) {
        workspace.name = name;
      }
    });
  }
});

export const {
  deleteWorkspace,
  createWorkspace,
  setActiveWorkspace,
} = workspaceSlice.actions;

export default workspaceSlice.reducer;
