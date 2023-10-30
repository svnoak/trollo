import React, { useEffect, useMemo, useRef, useState } from "react";
import Task from "./Task";
import "./Lane.css";
import { useDispatch, useSelector } from "react-redux";
import { selectTasksByLaneId } from "../../store/selectors/taskSelector";
import { ThunkDispatch } from "@reduxjs/toolkit";
import {
  createTaskAsync,
  fetchAllLaneTasksAsync,
  moveTaskAsync,
} from "../../store/thunks/taskThunk";
import { deleteLaneAsync, renameLaneAsync } from "../../store/thunks/laneThunk";
import { SortableContext, useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { RxDragHandleVertical } from "react-icons/rx";
import { RiDeleteBin6Line } from "react-icons/ri";
import { DndContext, DragEndEvent } from "@dnd-kit/core";

type LaneProps = {
  lane: Lane;
};

/**
 * The lane component.
 * This component displays a lane and its tasks.
 * @param lane - The lane object to be displayed.
 * @returns A React component that displays the lane item.
 * 
 * TODO:
 * - Proper rerendering of tasks when a task or lane is moved
 */
export default function Lane({ lane }: LaneProps) {
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

  const tasks: Task[] = useSelector(selectTasksByLaneId(lane.id));

  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [laneName, setLaneName] = useState<string>(lane.name);
  const inputRef = useRef<HTMLInputElement>(null);

    /**
     * Fetches the tasks for the lane.
     */
  function fetchTasks() {
    if (!tasks) {
      dispatch(fetchAllLaneTasksAsync(lane.id));
    }
  }

    /**
     * Handles the add task event.
     */
  function addTaskHandler() {
    dispatch(createTaskAsync(lane.id));
  }

  /**
   * Handles editing the lane name.
   * @param event - The edit lane event.
   */
  function handleEditLane(
    event: React.MouseEvent<HTMLSpanElement, MouseEvent>
  ) {
    event?.stopPropagation();
    setIsEditing(true);
    inputRef.current?.focus();
  }

  /**
   * Handles the delete lane event.
   */
  function handleDelete() {
    dispatch(deleteLaneAsync(lane.id));
  }

  /**
   * Fetches the tasks for the lane when the lane is first rendered.
   */
  useEffect(() => {
    fetchTasks();
  }, [dispatch, lane.id, tasks]);

  useEffect(() => {}, [tasks]);

  const memoizedTasks = useMemo(() => tasks || [], [tasks]);

  /**
   * Focuses the lane name input when the lane name is being edited.
   */
  useEffect(() => {
    if (isEditing && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isEditing]);

  /**
   * Handles the click outside event of the lane name input to stop the edit and submit.
   * @param event - The click event.
   * @returns
   */
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        inputRef.current &&
        !inputRef.current.contains(event.target as Node)
      ) {
        submitLaneEdit(laneName);
      }
    }

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === "Enter" && inputRef.current) {
        submitLaneEdit(laneName);
      }
    }

    document.addEventListener("click", handleClickOutside);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("click", handleClickOutside);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [laneName]);

  /**
   * Submits the lane name edit.
   * @param name - The new name of the lane.
   */
  async function submitLaneEdit(name: string) {
    setIsEditing(false);
    const response = await dispatch(renameLaneAsync({ id: lane.id, name }));
    if ((response as { error?: string }).error) {
      setLaneName(lane.name);
      alert("Failed to update workspace name");
    }
  }

/**
 * Handles the drag end event of a task.
 * @param event - The drag end event.
 */
function handleOnDragEnd(event: DragEndEvent) {
    const { active, over } = event;
    if (!active || !over) return;
    const targetTaskId = over.id;
    const sourceTaskId = active.id as number;

    console.log(targetTaskId, sourceTaskId);
    const sourceTask = memoizedTasks.find((task) => task.id === sourceTaskId);
    const targetTask = memoizedTasks.find((task) => task.id === targetTaskId);

    if (!targetTask || !sourceTask) return;

    const payload = {
        taskId: sourceTaskId as number,
        sourceLaneId: sourceTask.laneId as number,
        destinationLaneId: targetTask.laneId as number,
        sourceIndex: sourceTask.position as number,
        destinationIndex: targetTask.position as number,
    };
    dispatch(moveTaskAsync(payload));
}

  const { attributes, listeners, setNodeRef, transform, transition } =
    useSortable({
      id: lane.id,
    });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  return (
    <li
      key={lane.id}
      className={`lane`}
      ref={(node) => {
        setNodeRef(node);
      }}
      style={style}
    >
      <div className="lane-header">
        {isEditing ? (
          <input
            type="text"
            ref={inputRef}
            value={laneName}
            onChange={(event) => setLaneName(event.target.value)}
          />
        ) : (
          <h1 onDoubleClick={handleEditLane} className="lane-title">
            {laneName}
          </h1>
        )}
        <button onClick={handleDelete}>
          <RiDeleteBin6Line />
        </button>
        <div className="drag-handle" {...listeners} {...attributes}>
          <RxDragHandleVertical />
        </div>
      </div>
      <DndContext onDragEnd={handleOnDragEnd}>
        <ul className="tasks-list">
          <SortableContext items={memoizedTasks.map((task) => task.id)}>
            {memoizedTasks &&
              memoizedTasks.map((task: Task) => (
                <Task task={task} key={task.id} />
              ))}
            <li>
              <button className="add-task-button" onClick={addTaskHandler}>
                + Add Task
              </button>
            </li>
          </SortableContext>
        </ul>
      </DndContext>
    </li>
  );
}
