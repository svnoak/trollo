import { MdOutlineEdit } from "react-icons/md"
import { RiDeleteBin6Line } from "react-icons/ri"
import './Task.css'

type TaskProps = {
    task: Task
}

export default function Task({task}: TaskProps) {
    const {name, description, id} = task

    console.log("TASK", task);
    return(
        <li key={id}>
            <div className="task">
                <span>{name}</span>
                <span>{description}</span>
            </div>
            <div>
                <button><MdOutlineEdit /></button>
                <button><RiDeleteBin6Line /></button>
            </div>
        </li>
    )
}