import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { createWorkspaceAsync, fetchWorkspaces } from '../../store/thunks/workspaceThunk';
import WorkspaceMenuItem from './WorkspaceMenuItem';
import './WorkspaceMenu.css';
import {ThunkDispatch} from "@reduxjs/toolkit";
import { selectWorkspaces } from '../../store/selectors/workspaceSelector';

/**
 * The workspace menu component.
 * @returns WorkspaceMenu component
 */
export default function WorkspaceMenu() {

  const workspaces = useSelector(selectWorkspaces);
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

  useEffect(() => {
    const fetchWorkspacesData = async () => {
      try {
        dispatch(fetchWorkspaces())
      } catch (error) {
        console.error('Failed to fetch workspaces', error);
      }
    }
    fetchWorkspacesData();
  }, [dispatch]);


  const handleCreateWorkspace = async () => {
    try {
      await dispatch(createWorkspaceAsync());
      dispatch(fetchWorkspaces());
    } catch (error) {
      alert('Failed to create workspace');
    }
  };

  return(
    <div className="workspace-menu">
    <button onClick={handleCreateWorkspace}>+ New Workspace</button>
    <ul className="workspace-menu-list">
      {workspaces && workspaces.map((workspace: Workspace) => (
        <WorkspaceMenuItem
          key={workspace.id}
          workspace={workspace}
        />
      ))}
    </ul>
  </div>
  )
}
