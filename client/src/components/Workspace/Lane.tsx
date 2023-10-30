import { useEffect } from "react";
import Task from "./Task";
import './Lane.css'
import { useDispatch, useSelector } from "react-redux";
import { selectTasksByLaneId } from "../../store/selectors/taskSelector";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { createTaskAsync, fetchAllLaneTasksAsync } from "../../store/thunks/taskThunk";

type LaneProps = {
    lane: Lane
}

export default function  Lane({lane}: LaneProps) {
    const {name, id} = lane
    const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();

    const tasks: Task[] = useSelector(selectTasksByLaneId(id));
    
    function fetchTasks() {
        if(tasks) return;
        dispatch(fetchAllLaneTasksAsync(id));
    }

    function addTaskHandler() {
        dispatch(createTaskAsync(id));
    }

    useEffect(() => {
        fetchTasks();
    }, [])

    useEffect(() => {console.log("NEW TASKS")}, [tasks])

    return(
        <li key={id} className="lane">
            <h3 className="lane-header">{name}</h3>
            <ul>
                {tasks && tasks.map((task: Task) => <Task task={task} />)}
                <li className="add-task"><button className="add-task-button" onClick={addTaskHandler}>+ Add Task</button></li>
            </ul>
        </li>
    )

}