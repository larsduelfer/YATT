package com.novatecgmbh.eventsourcing.axon.project.task.query

import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskId
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskQueryResult
import com.novatecgmbh.eventsourcing.axon.project.task.api.TaskStatusEnum
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tasks")
class TaskProjection(
    @EmbeddedId var identifier: TaskId,
    @Column(nullable = false) var version: Long,
    @Embedded
    @AttributeOverride(name = "identifier", column = Column(name = "projectId", nullable = false))
    var projectId: ProjectId,
    @Column(nullable = false) var name: String,
    var description: String?,
    @Column(nullable = false) var startDate: LocalDate,
    @Column(nullable = false) var endDate: LocalDate,
    @Column(nullable = false) var status: TaskStatusEnum
) {
  fun toQueryResult() =
      TaskQueryResult(identifier, projectId, name, description, startDate, endDate, status)
}