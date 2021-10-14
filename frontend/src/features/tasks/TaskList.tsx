import {ReactJSXElement} from "@emotion/react/types/jsx-namespace";
import {CircularProgress, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import {selectTaskByProjectIdAndTaskId, useGetTasksByProjectQuery} from "./taskSlice";
import {useHistory, useParams} from "react-router-dom";
import {useAppSelector} from "../../app/hooks";
import {EntityId} from "@reduxjs/toolkit";
import {Scaffold} from "../../components/Scaffold";
import React from "react";
import {selectIdsFromResult} from "../../app/rtkQueryHelpers";
import {TableToolbar} from "../../components/TableToolbar";

export function TaskList() {
    const history = useHistory();
    const {id: projectId} = useParams<{ id: string }>()

    const {
        data: taskIds,
        isLoading,
        isSuccess,
        isError,
        error
    } = useGetTasksByProjectQuery(projectId, {selectFromResult: selectIdsFromResult});

    let content: ReactJSXElement;
    if (isLoading) {
        content = <CircularProgress/>;
    } else if (isSuccess && taskIds) {
        content = (
            <TableContainer sx={{minWidth: 1000}} component={Paper}>
                <TableToolbar
                    title={''}
                    tooltip={'Create Task'}
                    onClick={() => history.push(`/projects/${projectId}/tasks/create`)}
                />
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell>Start Date</TableCell>
                            <TableCell>End Date</TableCell>
                            <TableCell>Status</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            taskIds.map(
                                (taskId: EntityId) => <TaskListRow projectId={projectId} taskId={taskId}/>
                            )
                        }
                    </TableBody>
                </Table>
            </TableContainer>
        );
    } else if (isError) {
        content = <div>{error}</div>;
    } else {
        return null;
    }

    return (
        <Scaffold title={"Projects / Tasks"}>
            {content}
        </Scaffold>
    );
}

function TaskListRow({projectId, taskId}: { projectId: EntityId, taskId: EntityId }) {
    const task = useAppSelector((state) => selectTaskByProjectIdAndTaskId(state, projectId, taskId))
    if (task)
        return (
            <TableRow hover>
                <TableCell>{task.name}</TableCell>
                <TableCell>{new Date(task.startDate).toLocaleDateString()}</TableCell>
                <TableCell>{new Date(task.endDate).toLocaleDateString()}</TableCell>
                <TableCell>{task.status}</TableCell>
            </TableRow>
        );
    else {
        return null;
    }
}