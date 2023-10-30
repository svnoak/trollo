import { MdOutlineEdit } from "react-icons/md";
import { RiDeleteBin6Line } from "react-icons/ri";
import "./Task.css";
import { useDispatch } from "react-redux";
import { Action, ThunkDispatch } from "@reduxjs/toolkit";
import { RootState } from "../../store/configureStore";
import { deleteTaskAsync, updateTaskAsync } from "../../store/thunks/taskThunk";
import { useState } from "react";
import {CSS} from '@dnd-kit/utilities';
import { useSortable } from "@dnd-kit/sortable";

type TaskProps = {
    task: Task;
};

export default function Task({ task }: TaskProps) {
    const { id, name, description } = task;

    const dispatch = useDispatch<ThunkDispatch<RootState, null, Action>>();
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [taskName, setTaskName] = useState<string>(name);

    function handleDelete() {
        dispatch(deleteTaskAsync(task));
    }

    function handleEdit() {
        setIsEditing(true);
    }

    function handleSave() {
        const updatedTask = {...task};
        updatedTask.name = taskName;
        dispatch(updateTaskAsync(updatedTask));
        setIsEditing(false);
    }

    function handleCancel() {
        setTaskName(name);
        setIsEditing(false);
    }

    const { attributes, listeners, setNodeRef, transform, transition } = useSortable({
        id: id,
      });
    
      const style = {
        transform: CSS.Transform.toString(transform),
        transition,
      };

    return (
        <li 
        className={`task ${isEditing ? "editing" : ""}`}
        ref={setNodeRef}
        {...attributes}
        style={style}
        {...listeners}
        >
            <div className="task-info">
                {isEditing ? (
                    <input
                        type="text"
                        value={taskName}
                        onChange={(e) => setTaskName(e.target.value)}
                    />
                ) : (
                    <>
                        <span>{name}</span>
                        <span>{description}</span>
                    </>
                )}
            </div>
            <div className={`task-options`}>
                {isEditing ? (
                    <>
                        <button onClick={handleSave}>Save</button>
                        <button onClick={handleCancel}>Cancel</button>
                    </>
                ) : (
                    <>
                        <button onClick={handleEdit}>
                            <MdOutlineEdit />
                        </button>
                        <button onClick={handleDelete}>
                            <RiDeleteBin6Line />
                        </button>
                    </>
                )}
            </div>
        </li>
    );
}
