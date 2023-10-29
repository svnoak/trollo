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

  export default function WorkspaceMenuItem({workspace}: WorkspaceMenuItemProps) {
    const {id, name} = workspace
    
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [workspaceName, setWorkspaceName] = useState<string>(name);
    const [isActive, setIsActive] = useState<boolean>(false);
    const activeWorkspace = useSelector(selectActiveWorkspace);
    const inputRef = useRef<HTMLInputElement>(null);

    const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

    useEffect((
    ) => {
        setIsActive(activeWorkspace?.id === workspace.id);
    }, [activeWorkspace, workspace]);

    useEffect(() => {
        if (isEditing && inputRef.current) {
            inputRef.current.focus();
        }
    }, [isEditing]);

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (inputRef.current && !inputRef.current.contains(event.target as Node)) {
                console.log('Clicked outside');
                submitWorkspaceEdit(workspaceName);
            }
        }

        function handleKeyDown(event: KeyboardEvent) {
            if (event.key === 'Enter' && inputRef.current) {
                console.log('Pressed enter');
                console.log("Workspace name", workspaceName);
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

    async function submitWorkspaceEdit(name: string) {
        setIsEditing(false);
        console.log('Submitting workspace edit', name);
            const response = await dispatch(updateWorkspaceNameAsync({id, name}));
            if((response as {error?: string}).error) {
                setWorkspaceName(workspace.name);
                alert('Failed to update workspace name')
            }
    }

    function handleEdit(event: React.MouseEvent<HTMLButtonElement>) {
        event?.stopPropagation();
        console.log('Edit button clicked');
        setIsEditing(true);
        inputRef.current?.focus();
    }

    function handleDelete(event: React.MouseEvent<HTMLButtonElement>) {
        event?.stopPropagation();
        const response = dispatch(deleteWorkspaceAsync(id));
        if((response as {error?: string}).error) {
            alert('Failed to delete workspace');
        }
    }

    function handleActive() {
        console.log("Clicked");
        dispatch(setActiveWorkspace(workspace));
    }

    console.log('WorkspaceMenuItem rendered', workspace.id);

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

