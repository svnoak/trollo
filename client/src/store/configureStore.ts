import { combineReducers, configureStore } from '@reduxjs/toolkit';
import workspaceReducer from './slices/workspaceSlice';
import laneReducer from './slices/laneSlice';
import taskReducer from './slices/taskSlice';

const rootReducer = combineReducers({
  workspace: workspaceReducer,
  lane: laneReducer,
  task: taskReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

const store = configureStore({
  reducer: rootReducer,
});

export default store;