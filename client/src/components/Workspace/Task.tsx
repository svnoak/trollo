import { MdOutlineEdit } from "react-icons/md"
import { RiDeleteBin6Line } from "react-icons/ri"

type TaskProps = {
    task: Task
}

export default function Task({task}: TaskProps) {
    const {name, description, id} = task
    return(
        <li key={id}>
            <div>
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