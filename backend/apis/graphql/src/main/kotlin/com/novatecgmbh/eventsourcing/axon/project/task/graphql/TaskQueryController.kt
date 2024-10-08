package com.novatecgmbh.eventsourcing.axon.project.task.graphql

import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectQueryResult
import com.novatecgmbh.eventsourcing.axon.project.task.api.*
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import org.axonframework.extensions.kotlin.query
import org.axonframework.extensions.kotlin.queryMany
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@Controller
class TaskQueryController(val queryGateway: QueryGateway) {

    @QueryMapping
    fun task(@Argument identifier: TaskId): CompletableFuture<TaskQueryResult> =
        queryGateway.query(TaskQuery(identifier))

    // This PR https://github.com/spring-projects/spring-graphql/pull/324
    // might fix the issue using @BatchMapping together with additional arguments.
    // Currently, this is not working, that is why we replaced it with a simple @SchemaMapping

    //  @BatchMapping
    //  fun tasks(
    //      projects: List<ProjectQueryResult>,
    //      @Argument from: LocalDate?,
    //      @Argument to: LocalDate?
    //  ): Mono<Map<ProjectQueryResult, List<TaskQueryResult>>> =
    //      queryGateway
    //          .queryMany<TaskQueryResult, TasksByMultipleProjectsQuery>(
    //              TasksByMultipleProjectsQuery(
    //                  projects.map(ProjectQueryResult::identifier).toSet(), from, to))
    //          .thenApply {
    //            it.groupBy { task ->
    //              projects.first { project -> project.identifier == task.projectId }
    //            }
    //          }
    //          .toMono()

    @QueryMapping
    fun tasks(@Argument projectIdentifier: ProjectId): CompletableFuture<List<TaskQueryResult>> =
        queryGateway.queryMany(TasksByProjectQuery(projectIdentifier))

    @SubscriptionMapping("tasks")
    fun taskUpdates(@Argument projectIdentifier: ProjectId): Flux<TaskQueryResult> {
        val query =
            queryGateway.subscriptionQuery(
                TasksByProjectQuery(projectIdentifier),
                ResponseTypes.multipleInstancesOf(TaskQueryResult::class.java),
                ResponseTypes.instanceOf(TaskQueryResult::class.java)
            )

        return query.updates().doFinally { query.cancel() }
    }

    @SchemaMapping(typeName = "Project")
    fun tasks(
        project: ProjectQueryResult,
        @Argument from: LocalDate?,
        @Argument to: LocalDate?
    ): CompletableFuture<List<TaskQueryResult>> =
        queryGateway.queryMany(TasksByMultipleProjectsQuery(setOf(project.identifier), from, to))
}
