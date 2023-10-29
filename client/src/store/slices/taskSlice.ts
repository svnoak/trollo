import { createSlice } from "@reduxjs/toolkit";
import { fetchTasksAsync, renameTaskAsync, updateTaskDescriptionAsync } from "../thunks/taskThunk";


export type TaskState = {
    tasks: Task[];
    task: Task | undefined;
};

const taskSlice = createSlice({
    name: "task",
    initialState: {
        tasks: [] as Task[],
        task: undefined as Task | undefined,
    },
    reducers: {
        renameTask(state, action) {
            const { id, name } = action.payload;
            const task = state.tasks.find((task) => task.id === id);
            if (task) {
                task.name = name;
            }
        },

        updateTaskDescription(state, action) {
            const { id, description } = action.payload;
            const task = state.tasks.find((task) => task.id === id);
            if (task) {
                task.description = description;
            }
        },
    },
    extraReducers: (builder) => {
        builder.addCase(fetchTasksAsync.fulfilled, (state, action) => {
            state.tasks = action.payload;
        });
        
        builder.addCase(renameTaskAsync.fulfilled, (state, action) => {
            const { id, name } = action.payload;
            const task = state.tasks.find((task) => task.id === id);
            if (task) {
                task.name = name;
            }
        });

        builder.addCase(updateTaskDescriptionAsync.fulfilled, (state, action) => {
            const { id, description } = action.payload;
            const task = state.tasks.find((task) => task.id === id);
            if (task) {
                task.description = description;
            }
        });
    },
});

export const { renameTask, updateTaskDescription } = taskSlice.actions;
export default taskSlice.reducer;