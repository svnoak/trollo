import { createSelector } from '@reduxjs/toolkit';
import { RootState } from '../configureStore'

export const selectWorkspaces = (state: RootState) => state.workspace.workspacesArray;

export const selectWorkspacesArray = createSelector(
  selectWorkspaces,
  (workspaces) => Object.values(workspaces)
);

export const selectActiveWorkspace = (state: RootState) => state.workspace.workspace;

export const selectActiveWorkspaceLanes = createSelector(
  selectActiveWorkspace,
  (workspace) => workspace?.lanes
);