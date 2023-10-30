import 'symbol-observable';
import { combineReducers, configureStore } from '@reduxjs/toolkit';
import workspaceReducer from './slices/workspaceSlice';
import laneReducer from './slices/laneSlice';

const rootReducer = combineReducers({
  workspace: workspaceReducer,
  lane: laneReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

const store = configureStore({
  reducer: rootReducer,
  devTools: false,
});

export default store;