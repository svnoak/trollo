import { MdOutlineEdit } from "react-icons/md";
import { RiDeleteBin6Line } from "react-icons/ri";
import "./Task.css";
import { useDispatch } from "react-redux";
import { Action, ThunkDispatch } from "@reduxjs/toolkit";
import { RootState } from "../../store/configureStore";
import { deleteTaskAsync } from "../../store/thunks/taskThunk";
import { Draggable } from "react-beautiful-dnd";

type TaskProps = {
  task: Task;
};

export default function Task({ task }: TaskProps) {
  const { name, description} = task;

  const dispatch = useDispatch<ThunkDispatch<RootState, null, Action>>();

  function handleDelete() {
    console.log("Delete task");
    dispatch(deleteTaskAsync(task));
  }

  function handleEdit() {}

  return (
        <li className="task"> 
          <div className="task-info">
            <span>{name}</span>
            <span>{description}</span>
          </div>
          <div className="task-options">
            <button onClick={handleEdit}>
              <MdOutlineEdit />
            </button>
            <button onClick={handleDelete}>
              <RiDeleteBin6Line />
            </button>
          </div>
        </li>
      );
}
