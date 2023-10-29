import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { addLaneAsync, createWorkspaceAsync, deleteLaneAsync, deleteWorkspaceAsync, fetchWorkspaces, updateWorkspaceNameAsync } from "../thunks/workspaceThunk";

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
      const id = action.payload.id;
      state.workspace = state.workspacesArray.find(
        (workspace: { id: number }) => workspace.id === id
      );
    },

    addLane(state, action) {
        if(state.workspace) {
            state.workspace.lanes.push(action.payload);
        }
    },

    deleteLane(state, action) {
        const id = action.payload;
        if(state.workspace) {
            state.workspace.lanes = state.workspace.lanes.filter(
                (lane: { id: number }) => lane.id !== id
            );
        }
    },

    moveLane(state, action) {
        const { sourceIndex, destinationIndex } = action.payload;
        if(state.workspace) {
            const lane = state.workspace.lanes.splice(sourceIndex, 1)[0];
            state.workspace.lanes.splice(destinationIndex, 0, lane);
        }
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchWorkspaces.fulfilled, (state, action) => {
      state.workspacesArray = action.payload;
    });

    builder.addCase(deleteWorkspaceAsync.fulfilled, (state, action) => {
      const id = action.payload;
      console.log(action.payload);
      state.workspacesArray = state.workspacesArray.filter(
        (workspace: { id: number }) => workspace.id !== id
      );
    })

    builder.addCase(createWorkspaceAsync.fulfilled, (state, action) => {
      state.workspacesArray.push(action.payload);
    });

    builder.addCase(addLaneAsync.fulfilled, (state, action) => {
      if(state.workspace) {
        state.workspace.lanes.push(action.payload);
      }
    });

    builder.addCase(deleteLaneAsync.fulfilled, (state, action) => {
      const id: number = action.payload as unknown as number;
      if(state.workspace) {
        state.workspace.lanes = state.workspace.lanes.filter(
          (lane: { id: number }) => lane.id !== id
        );
      }
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
  addLane,
  deleteLane,
  moveLane,
} = workspaceSlice.actions;

export default workspaceSlice.reducer;
