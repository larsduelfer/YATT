package com.novatecgmbh.eventsourcing.axon.project.task.query

import com.novatecgmbh.eventsourcing.axon.application.auditing.AUDIT_USER_ID_META_DATA_KEY
import com.novatecgmbh.eventsourcing.axon.project.authorization.ProjectAuthorizationService
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskQuery
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskQueryResult
import com.novatecgmbh.eventsourcing.axon.project.task.api.TasksByProjectQuery
import com.novatecgmbh.eventsourcing.axon.user.api.UserId
import java.util.*
import org.axonframework.messaging.annotation.MetaDataValue
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class TaskQueryHandler(
    private val repository: TaskProjectionRepository,
    private val authService: ProjectAuthorizationService
) {

  @QueryHandler
  fun handle(
      query: TasksByProjectQuery,
      @MetaDataValue(AUDIT_USER_ID_META_DATA_KEY) userId: String
  ): Iterable<TaskQueryResult> =
      authService.runWhenAuthorizedForProject(UserId(userId), query.projectId) {
        repository.findAllByProjectId(query.projectId).map { it.toQueryResult() }
      }

  @QueryHandler
  fun handle(
      query: TaskQuery,
      @MetaDataValue(AUDIT_USER_ID_META_DATA_KEY) userId: String
  ): Optional<TaskQueryResult> =
      repository.findById(query.taskId).map { it.toQueryResult() }?.let {
        authService.runWhenAuthorizedForProject(UserId(userId), it.get().projectId) { it }
      }
          ?: Optional.empty()
}