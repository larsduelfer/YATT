package com.novatecgmbh.eventsourcing.axon.project.task.query

import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantQuery
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantQueryResult
import com.novatecgmbh.eventsourcing.axon.project.task.api.*
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskStatusEnum.*
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.axonframework.eventhandling.SequenceNumber
import org.axonframework.extensions.kotlin.emit
import org.axonframework.extensions.kotlin.queryOptional
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("task-projector")
class TaskProjector(
    private val repository: TaskProjectionRepository,
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val queryGateway: QueryGateway
) {
    @EventHandler
    fun on(event: TaskCreatedEvent, @SequenceNumber aggregateVersion: Long) {
        saveProjection(
            TaskProjection(
                identifier = event.identifier,
                version = aggregateVersion,
                projectId = event.projectId,
                name = event.name,
                description = event.description,
                startDate = event.startDate,
                endDate = event.endDate,
                status = PLANNED,
                participantId = null,
                todos = mutableListOf()
            )
        )
    }

    @EventHandler
    fun on(event: TaskRenamedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.name = event.name
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskDescriptionUpdatedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.description = event.description
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskRescheduledEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.startDate = event.startDate
            it.endDate = event.endDate
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskAssignedEvent, @SequenceNumber aggregateVersion: Long) {
        val participant =
            queryGateway
                .queryOptional<ParticipantQueryResult, ParticipantQuery>(
                    ParticipantQuery(event.assignee)
                )
                .get()
        updateProjection(event.identifier) { task ->
            task.participantId = event.assignee
            task.assigneeFirstName = participant.map { it.userFirstName }.orElse(null)
            task.assigneeLastName = participant.map { it.userLastName }.orElse(null)
            task.assigneeCompanyName = participant.map { it.companyName }.orElse(null)
            task.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskUnassignedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.participantId = null
            it.assigneeFirstName = null
            it.assigneeLastName = null
            it.assigneeCompanyName = null
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskStartedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.status = STARTED
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TaskCompletedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.status = COMPLETED
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TodoAddedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.todos.add(Todo(event.todoId, event.description, event.isDone))
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TodoRemovedEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.todos.removeIf { todo -> todo.todoId == event.todoId }
            it.version = aggregateVersion
        }
    }

    @EventHandler
    fun on(event: TodoMarkedAsDoneEvent, @SequenceNumber aggregateVersion: Long) {
        updateProjection(event.identifier) {
            it.todos.first { todo -> todo.todoId == event.todoId }.isDone = true
            it.version = aggregateVersion
        }
    }

    private fun updateProjection(identifier: TaskId, stateChanges: (TaskProjection) -> Unit) {
        repository.findById(identifier).get().also {
            stateChanges.invoke(it)
            saveProjection(it)
        }
    }

    private fun saveProjection(projection: TaskProjection) {
        repository.save(projection).also { savedProjection ->
            updateQuerySubscribers(savedProjection)
        }
    }

    private fun updateQuerySubscribers(task: TaskProjection) {
        queryUpdateEmitter.emit<TaskQuery, TaskQueryResult>(task.toQueryResult()) { query ->
            query.taskId == task.identifier
        }

        queryUpdateEmitter.emit<TasksByProjectQuery, TaskQueryResult>(task.toQueryResult()) { query
            ->
            query.projectId == task.projectId
        }
    }

    @ResetHandler fun reset() = repository.deleteAll()
}
