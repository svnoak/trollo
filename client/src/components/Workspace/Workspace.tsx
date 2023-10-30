import { useEffect, useMemo } from "react";
import "./Workspace.css";
import Lane from "./Lane";
import { useDispatch, useSelector } from "react-redux";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { fetchAllWorkspaceLanes, moveLaneAsync } from "../../store/thunks/laneThunk";
import { RootState } from "../../store/configureStore";
import { createLaneAsync } from "../../store/thunks/laneThunk";
import { DndContext, DragEndEvent } from "@dnd-kit/core";
import { SortableContext } from "@dnd-kit/sortable";

/**
 * The workspace component.
 * This component displays the workspace and its lanes.
 * 
 * @returns A React component that displays the workspace.
 * 
 * TODO:
 */
export default function Workspace() {
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();
  const lanes = useSelector((state: RootState) => state.lane.lanes);
  const activeWorkspace = useSelector(
    (state: RootState) => state.workspace.workspace
  );

  /**
   * Loads the lanes for the workspace.
   * @returns 
   */
  function loadLanes() {
    if (!activeWorkspace) return;
    dispatch(fetchAllWorkspaceLanes(activeWorkspace.id));
  }

  /**
   * Handles the add lane event.
   * @returns 
   */
  function handleAddLane() {
    if (!activeWorkspace) return;
    dispatch(createLaneAsync(activeWorkspace.id));
  }

  /**
   * Fetches the lanes for the workspace.
   */
  useEffect(() => {
    loadLanes();
  }, [dispatch, activeWorkspace]);

  const memoizedLanes = useMemo(() => lanes, [lanes]);

  /**
   * Handles the lane drag end event.
   * @param event - Drag end event.
   * @returns
   */
  function handleLaneDragEnd(event: DragEndEvent) {
    const { active, over } = event;
    if(!active || !over) return
    const targetLaneId = over.id;
    const sourceLaneId = active.id as number;
    const targetLane = memoizedLanes.find((lane) => lane.id === targetLaneId);
    if(!targetLane) return
    const newPosition = targetLane.position;

    dispatch(moveLaneAsync({ sourceLaneId, newPosition }));
  }


  /**
   * Displays the workspace lanes.
   * @returns Workspace lanes.
   */
  const WorkspaceLanes = () => {
   return(
    <DndContext onDragEnd={handleLaneDragEnd}>
          <ul className="lanes">
          <SortableContext items={memoizedLanes.map((lane) => lane.id)}>
            {memoizedLanes &&
              memoizedLanes.length > 0 &&
              memoizedLanes.map((lane: Lane) => <Lane key={lane.id} lane={lane} />)}
            <li className="add-lane">
              <button className="add-lane-button" onClick={handleAddLane}>
                + Add another lane
              </button>
            </li>
            </SortableContext>
          </ul>
        </DndContext>
   )
  }

  /**
   * Shows a placeholder when no workspace is selected.
   * @returns Placeholder workspace.
   */
  const PlaceHolderWorkspace = () => {
    return <h1>PLACEHOLDER</h1>;
  };

    return (
      <div className="workspace">
        <div className="board">
          {activeWorkspace ? <WorkspaceLanes /> : <PlaceHolderWorkspace />}
        </div>
      </div>
    );
  };
