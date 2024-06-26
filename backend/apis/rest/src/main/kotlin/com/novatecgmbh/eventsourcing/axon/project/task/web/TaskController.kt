package com.novatecgmbh.eventsourcing.axon.project.task.web

import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import com.novatecgmbh.eventsourcing.axon.project.task.api.*
import com.novatecgmbh.eventsourcing.axon.project.task.web.dto.*
import java.util.concurrent.CompletableFuture
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.extensions.kotlin.queryMany
import org.axonframework.extensions.kotlin.queryOptional
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
class TaskController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway,
) {

    @PostMapping("/v2/tasks")
    fun createTask(@RequestBody body: CreateTaskDto): CompletableFuture<String> =
        createTaskWithId(TaskId(), body)

    @PostMapping("/v2/tasks/{taskId}")
    fun createTaskWithId(
        @PathVariable("taskId") taskId: TaskId,
        @RequestBody body: CreateTaskDto
    ): CompletableFuture<String> = commandGateway.send(body.toCommand(taskId))

    @GetMapping("/v2/tasks/{taskId}")
    fun getTaskById(@PathVariable("taskId") taskId: TaskId): ResponseEntity<TaskQueryResult> =
        queryGateway
            .queryOptional<TaskQueryResult, TaskQuery>(TaskQuery(taskId))
            .get()
            .map { ResponseEntity(it, OK) }
            .orElse(ResponseEntity(NOT_FOUND))

    @GetMapping("/v2/tasks/{taskId}", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun getTaskByIdAndUpdates(@PathVariable("taskId") taskId: TaskId): Flux<TaskQueryResult> {
        val query =
            queryGateway.subscriptionQuery(
                TaskQuery(taskId),
                ResponseTypes.instanceOf(TaskQueryResult::class.java),
                ResponseTypes.instanceOf(TaskQueryResult::class.java)
            )

        return query.initialResult().concatWith(query.updates()).doFinally { query.cancel() }
    }

    @PostMapping("/v2/tasks/{taskId}/todos")
    fun addTodo(
        @PathVariable("taskId") taskId: TaskId,
        @RequestBody body: AddTodoDto
    ): CompletableFuture<String> = commandGateway.send(body.toCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/todos/{todoId}/markDone")
    fun markTodoAsDone(
        @PathVariable("taskId") taskId: TaskId,
        @PathVariable("todoId") todoId: TodoId
    ): CompletableFuture<String> = commandGateway.send(MarkTodoAsDoneCommand(taskId, todoId))

    @DeleteMapping("/v2/tasks/{taskId}/todos/{todoId}")
    fun removeTodo(
        @PathVariable("taskId") taskId: TaskId,
        @PathVariable("todoId") todoId: TodoId
    ): CompletableFuture<String> = commandGateway.send(RemoveTodoCommand(taskId, todoId))

    @GetMapping("/v2/projects/{projectId}/tasks")
    fun getTasksByProject(
        @PathVariable("projectId") projectId: ProjectId
    ): ResponseEntity<List<TaskQueryResult>> =
        ResponseEntity(
            queryGateway
                .queryMany<TaskQueryResult, TasksByProjectQuery>(TasksByProjectQuery(projectId))
                .get(),
            OK
        )

    @GetMapping("/v2/projects/{projectId}/tasks", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun getTasksByProjectAndUpdates(
        @PathVariable("projectId") projectId: ProjectId
    ): Flux<List<TaskQueryResult>> {
        val query =
            queryGateway.subscriptionQuery(
                TasksByProjectQuery(projectId),
                ResponseTypes.multipleInstancesOf(TaskQueryResult::class.java),
                ResponseTypes.instanceOf(TaskQueryResult::class.java)
            )

        return query.initialResult().concatWith(query.updates().map(::listOf)).doFinally {
            query.cancel()
        }
    }

    @PostMapping("/v2/tasks/{taskId}/rename")
    fun rename(
        @PathVariable("taskId") taskId: TaskId,
        @RequestBody body: RenameTaskDto
    ): CompletableFuture<String> = commandGateway.send(body.toCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/reschedule")
    fun reschedule(
        @PathVariable("taskId") taskId: TaskId,
        @RequestBody body: RescheduleTaskDto
    ): CompletableFuture<String> = commandGateway.send(body.toCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/assign")
    fun assign(
        @PathVariable("taskId") taskId: TaskId,
        @RequestBody body: AssignTaskDto
    ): CompletableFuture<String> = commandGateway.send(body.toCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/unassign")
    fun unassign(@PathVariable("taskId") taskId: TaskId): CompletableFuture<String> =
        commandGateway.send(UnassignTaskDto().toCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/start")
    fun start(@PathVariable("taskId") taskId: TaskId): CompletableFuture<String> =
        commandGateway.send(StartTaskCommand(taskId))

    @PostMapping("/v2/tasks/{taskId}/complete")
    fun complete(@PathVariable("taskId") taskId: TaskId): CompletableFuture<String> =
        commandGateway.send(CompleteTaskCommand(taskId))
}
