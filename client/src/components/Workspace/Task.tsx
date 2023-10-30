import { MdOutlineEdit } from "react-icons/md";
import { RiDeleteBin6Line, RiDragMove2Fill } from "react-icons/ri";
import "./Task.css";
import { useDispatch } from "react-redux";
import { Action, ThunkDispatch } from "@reduxjs/toolkit";
import { RootState } from "../../store/configureStore";
import { deleteTaskAsync, updateTaskAsync } from "../../store/thunks/taskThunk";
import { useState } from "react";
import { CSS } from "@dnd-kit/utilities";
import { useSortable } from "@dnd-kit/sortable";

type TaskProps = {
  task: Task;
};

/**
 * Task component that displays a single task item with options to edit, delete and drag.
 * @param task - The task object to be displayed.
 * @returns A React component that displays the task item.
 * 
 * TODO:
 * - Add task description
 * - Fix task-reordering through drag and drop
 * - Add task-reordering between lines
 */

export default function Task({ task }: TaskProps) {
  const { id, name, description } = task;

  const dispatch = useDispatch<ThunkDispatch<RootState, null, Action>>();
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [taskName, setTaskName] = useState<string>(name);

  /**
   * Handles the delete task event.
   * @param event - The delete task event.
   */
  function handleDelete(
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) {
    event.preventDefault();
    event.stopPropagation();
    dispatch(deleteTaskAsync(task));
  }

    /**
     * Handles the edit task event.
     * @param event - The edit task event.
     */
  function handleEdit(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    event.preventDefault();
    event.stopPropagation();
    setIsEditing(true);
  }

  /**
   * Handles the save task event.
   * @param event - The save task event.
   */
  function handleSave(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    event.preventDefault();
    event.stopPropagation();
    const updatedTask = { ...task };
    updatedTask.name = taskName;
    dispatch(updateTaskAsync(updatedTask));
    setIsEditing(false);
  }

  /**
   * Handles the cancel task edit event.
   * @param event - The cancel task event.
   */
  function handleCancel(
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) {
    event.preventDefault();
    event.stopPropagation();
    setTaskName(name);
    setIsEditing(false);
  }

  const { attributes, listeners, setNodeRef, transform, transition } =
    useSortable({
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
      style={style}
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
        <RiDragMove2Fill  {...listeners} {...attributes}/>
      </div>
    </li>
  );
}
