import React, { useEffect, useRef, useState } from "react";
import Task from "./Task";
import './Lane.css'
import { useDispatch, useSelector } from "react-redux";
import { selectTasksByLaneId } from "../../store/selectors/taskSelector";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { createTaskAsync, fetchAllLaneTasksAsync } from "../../store/thunks/taskThunk";
import { renameLaneAsync } from "../../store/thunks/laneThunk";

type LaneProps = {
  lane: Lane
}

export default function Lane({ lane }: LaneProps) {
  const { name, id } = lane
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

  const tasks: Task[] = useSelector(selectTasksByLaneId(id));

  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [laneName, setLaneName] = useState<string>(name);
  const inputRef = useRef<HTMLInputElement>(null);

  function fetchTasks() {
    if (tasks) return;
    dispatch(fetchAllLaneTasksAsync(id));
  }

  function addTaskHandler() {
    dispatch(createTaskAsync(id));
  }

  function handleEditLane(event: React.MouseEvent<HTMLSpanElement, MouseEvent>) {
    event?.stopPropagation();
    setIsEditing(true);
    inputRef.current?.focus();
  }

  useEffect(() => {
    fetchTasks();
  }, [])

  useEffect(() => { }, [tasks])

  useEffect(() => {
    if (isEditing && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isEditing]);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (inputRef.current && !inputRef.current.contains(event.target as Node)) {
        submitLaneEdit(laneName);
      }
    }

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === 'Enter' && inputRef.current) {
        submitLaneEdit(laneName);
      }
    }

    document.addEventListener('click', handleClickOutside);
    document.addEventListener('keydown', handleKeyDown);

    return () => {
      document.removeEventListener('click', handleClickOutside);
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [laneName]);

  async function submitLaneEdit(name: string) {
    setIsEditing(false);
    const response = await dispatch(renameLaneAsync({ id, name }));
    if ((response as { error?: string }).error) {
      setLaneName(lane.name);
      alert('Failed to update workspace name')
    }
  }

  return (
      <li key={id} className="lane">
        {
          isEditing ? (
            <input type="text" ref={inputRef} value={laneName} onChange={(event) => setLaneName(event.target.value)} />
          ) : (
            <h1 onDoubleClick={handleEditLane}>{laneName}</h1>
          )
        }
            <ul className="tasks-list">
              {tasks && tasks.map((task: Task) => (
                      <Task task={task} key={task.id} />
              ))}
              <li><button className="add-task-button" onClick={addTaskHandler}>+ Add Task</button></li>
            </ul>
      </li>
  )
}