package com.novatecgmbh.eventsourcing.axon.project.participant.query

import com.novatecgmbh.eventsourcing.axon.application.auditing.AuditUserId
import com.novatecgmbh.eventsourcing.axon.project.authorization.ProjectAuthorizationService
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantByMultipleProjectsQuery
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantByProjectQuery
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantQuery
import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantQueryResult
import com.novatecgmbh.eventsourcing.axon.user.api.UserId
import java.util.*
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class ParticipantQueryHandler(
    private val repository: ParticipantProjectionRepository,
    private val authService: ProjectAuthorizationService
) {

    @QueryHandler
    fun handle(
        query: ParticipantQuery,
        @AuditUserId userId: String
    ): Optional<ParticipantQueryResult> =
        repository
            .findById(query.participantId)
            .map { it.toQueryResult() }
            ?.let {
                authService.runWhenAuthorizedForProject(UserId(userId), it.get().projectId) { it }
            } ?: Optional.empty()

    @QueryHandler
    fun handle(
        query: ParticipantByProjectQuery,
        @AuditUserId userId: String
    ): Iterable<ParticipantQueryResult> =
        authService.runWhenAuthorizedForProject(UserId(userId), query.projectId) {
            repository.findAllByProjectId(query.projectId).map { it.toQueryResult() }
        }

    @QueryHandler
    fun handle(
        query: ParticipantByMultipleProjectsQuery,
        @AuditUserId userId: String
    ): Iterable<ParticipantQueryResult> =
        authService.runWhenAuthorizedForAllProjects(UserId(userId), query.projectIds) {
            repository.findAllByProjectIdIn(query.projectIds).map { it.toQueryResult() }
        }
}
