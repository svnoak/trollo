import { useEffect, useState } from "react";
import Task from "./Task";
import './Lane.css'

type LaneProps = {
    lane: Lane
}

export default function  Lane({lane}: LaneProps) {
    const {name, id} = lane

    function fetchTasks() {
        fetch(`http://localhost:3000/api/lanes/${id}/tasks`)
        .then(response => response.json())
        .then(data => setTasks(data))
    }

    const [tasks, setTasks] = useState<Array<Task>>([])
    useEffect(() => {
        fetchTasks();
    }, [])

    return(
        <li key={id} className="lane">
            <h3>{name}</h3>
            <ul>
                {tasks.map((task: Task) => <Task task={task} />)}
                <li><button>+ Add Task</button></li>
            </ul>
        </li>
    )

}