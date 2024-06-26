package com.novatecgmbh.eventsourcing.axon.project.task.rsocket.dto

import com.novatecgmbh.eventsourcing.axon.project.task.api.RemoveTodoCommand
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskId
import com.novatecgmbh.eventsourcing.axon.project.task.api.TodoId

data class RemoveTodoDto(val taskId: TaskId, val todoId: TodoId) {
    fun toCommand() = RemoveTodoCommand(identifier = taskId, todoId = todoId)
}
