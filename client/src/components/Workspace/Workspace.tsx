import { useEffect } from "react";
import "./Workspace.css";
import Lane from "./Lane";
import { useDispatch, useSelector } from "react-redux";
import { ThunkDispatch } from "@reduxjs/toolkit";
import { fetchAllWorkspaceLanes } from "../../store/thunks/laneThunk";
import { RootState } from "../../store/configureStore";
import { addLaneAsync } from "../../store/thunks/workspaceThunk";

export default function Workspace() {
  const dispatch = useDispatch<ThunkDispatch<unknown, unknown, any>>();
  const lanes = useSelector((state: RootState) => state.lane.lanes);
  const activeWorkspace = useSelector((state: RootState) => state.workspace.workspace);
  
  function loadLanes() {
    if(!activeWorkspace) return;
    dispatch(fetchAllWorkspaceLanes(activeWorkspace.id));
  }

  function handleAddLane() {
    console.log("Add lane");
    if(!activeWorkspace) return;
    dispatch(addLaneAsync(activeWorkspace.id));
  }

  useEffect(() => {
    loadLanes();
  }, []);

  useEffect(() => {}, [lanes]);

  console.log(lanes);

  const PlaceHolderWorkspace = () => {
    return(
      <h1>PLACEHOLDER</h1>
    )
  }

  const WorkspaceLanes = () => {
    return(
      <div className="board">
        <ul className="lanes">
          {lanes && lanes.length > 0 && (
            lanes.map((lane: Lane) => (
              <Lane lane={lane} />
            ))
          )}
        </ul>
        <button className="add-lane" onClick={handleAddLane}>+ Add another lane</button>
      </div>
    )
  }

  return (
    <div className="workspace">
      {activeWorkspace ? <WorkspaceLanes /> : <PlaceHolderWorkspace />}
    </div>
  );
}
