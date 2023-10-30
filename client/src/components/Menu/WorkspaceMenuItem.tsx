import { RiDeleteBin6Line } from "react-icons/ri"
import { MdOutlineEdit } from "react-icons/md"
import './WorkspaceMenuItem.css'
import { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { deleteWorkspaceAsync, updateWorkspaceNameAsync } from "../../store/thunks/workspaceThunk";
import { setActiveWorkspace } from "../../store/slices/workspaceSlice";
import { selectActiveWorkspace } from "../../store/selectors/workspaceSelector";

type WorkspaceMenuItemProps = {
    workspace: Workspace;
  };

  /**
   * The workspace menu item component.
   * @param workspace The workspace object to be displayed.
   * @returns Workspace menu item component
   */
  export default function WorkspaceMenuItem({workspace}: WorkspaceMenuItemProps) {
    const {id, name} = workspace
    
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [workspaceName, setWorkspaceName] = useState<string>(name);
    const [isActive, setIsActive] = useState<boolean>(false);
    const activeWorkspace = useSelector(selectActiveWorkspace);
    const inputRef = useRef<HTMLInputElement>(null);

    const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

    /**
     * Sets the active workspace.
     */
    useEffect((
    ) => {
        setIsActive(activeWorkspace?.id === workspace.id);
    }, [activeWorkspace, workspace]);

    /**
     * Sets the focus on the input field when editing.
     */
    useEffect(() => {
        if (isEditing && inputRef.current) {
            inputRef.current.focus();
        }
    }, [isEditing]);

    /**
     * Handles the click outside event.
     */
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (inputRef.current && !inputRef.current.contains(event.target as Node)) {
                submitWorkspaceEdit(workspaceName);
            }
        }

        function handleKeyDown(event: KeyboardEvent) {
            if (event.key === 'Enter' && inputRef.current) {
                submitWorkspaceEdit(workspaceName);
            }
        }

        document.addEventListener('click', handleClickOutside);
        document.addEventListener('keydown', handleKeyDown);

        return () => {
            document.removeEventListener('click', handleClickOutside);
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, [workspaceName]);

    /**
     * Submits changes
     * @param name The new name of the workspace.
     */
    async function submitWorkspaceEdit(name: string) {
        setIsEditing(false);
            const response = await dispatch(updateWorkspaceNameAsync({id, name}));
            if((response as {error?: string}).error) {
                setWorkspaceName(workspace.name);
                alert('Failed to update workspace name')
            }
    }

    /**
     * Handles the edit of the workspace name
     * @param event The edit event.
     */
    function handleEdit(event: React.MouseEvent<HTMLButtonElement>) {
        event?.stopPropagation();
        setIsEditing(true);
        inputRef.current?.focus();
    }

    /**
     * Hanldes deletion of the workspace
     * @param event The delete event.
     */
    function handleDelete(event: React.MouseEvent<HTMLButtonElement>) {
        event?.stopPropagation();
        const response = dispatch(deleteWorkspaceAsync(id));
        if((response as {error?: string}).error) {
            alert('Failed to delete workspace');
        }
        dispatch(setActiveWorkspace(null));
    }

    /**
     * Handles the active workspace event.
     */
    function handleActive() {
        dispatch(setActiveWorkspace(workspace));
    }

    return(
        <li className={`workspace-menu-item-wrapper ${isActive ? 'active' : ''}`} key={id} onClick={handleActive}>
            <a className="workspace-menu-item">
                {
                    isEditing ? (
                        <input type="text" ref={inputRef} value={workspaceName} onChange={(event) => setWorkspaceName(event.target.value)} />
                    ) : (
                        <span>{workspaceName}</span>
                    )
                }
                {
                    isActive && (
                        <div className="menu-buttons">
                            <button onClick={handleEdit}><MdOutlineEdit /></button>
                            <button onClick={handleDelete}><RiDeleteBin6Line /></button>
                        </div>
                    )
                }
            </a>
        </li>
    );
}

