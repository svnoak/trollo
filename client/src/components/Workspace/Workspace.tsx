import React, { useEffect, useMemo } from "react";
import "./Workspace.css";
import Lane from "./Lane";
import { useDispatch, useSelector } from "react-redux";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { fetchAllWorkspaceLanes, moveLaneAsync } from "../../store/thunks/laneThunk";
import { RootState } from "../../store/configureStore";
import { createLaneAsync } from "../../store/thunks/laneThunk";
import { DndContext, DragEndEvent } from "@dnd-kit/core";
import { SortableContext } from "@dnd-kit/sortable";

export default function Workspace() {
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();
  const lanes = useSelector((state: RootState) => state.lane.lanes);
  const activeWorkspace = useSelector(
    (state: RootState) => state.workspace.workspace
  );

  function loadLanes() {
    if (!activeWorkspace) return;
    dispatch(fetchAllWorkspaceLanes(activeWorkspace.id));
  }

  function handleAddLane() {
    if (!activeWorkspace) return;
    dispatch(createLaneAsync(activeWorkspace.id));
  }

  useEffect(() => {
    loadLanes();
  }, [dispatch, activeWorkspace]);

  //useEffect(() => {}, [lanes]);

  const memoizedLanes = useMemo(() => lanes, [lanes]);

  const PlaceHolderWorkspace = () => {
    return <h1>PLACEHOLDER</h1>;
  };

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

    return (
      <div className="workspace">
        <div className="board">
          {activeWorkspace ? <WorkspaceLanes /> : <PlaceHolderWorkspace />}
        </div>
      </div>
    );
  };
