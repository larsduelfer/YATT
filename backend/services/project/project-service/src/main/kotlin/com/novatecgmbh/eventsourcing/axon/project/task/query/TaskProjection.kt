package com.novatecgmbh.eventsourcing.axon.project.task.query

import com.novatecgmbh.eventsourcing.axon.project.participant.api.ParticipantId
import com.novatecgmbh.eventsourcing.axon.project.project.api.ProjectId
import com.novatecgmbh.eventsourcing.axon.project.task.api.*
import jakarta.persistence.*
import jakarta.persistence.FetchType.EAGER
import java.io.Serializable
import java.time.LocalDate

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
    @Column(nullable = false) var status: TaskStatusEnum,
    @Embedded
    @AttributeOverride(name = "identifier", column = Column(name = "participantId"))
    var participantId: ParticipantId? = null,
    var assigneeFirstName: String? = null,
    var assigneeLastName: String? = null,
    var assigneeCompanyName: String? = null,
    @ElementCollection(fetch = EAGER)
    @CollectionTable(
        name = "task_todos",
        foreignKey = ForeignKey(name = "FK_TaskTodos_TaskIdentifier"),
        joinColumns = [JoinColumn(name = "task_identifier")],
        uniqueConstraints =
            [
                UniqueConstraint(
                    name = "UK_TaskTodos_Identifier",
                    columnNames = ["identifier", "task_identifier"],
                ),
            ],
    )
    var todos: MutableList<Todo>
) {
    fun toQueryResult() =
        TaskQueryResult(
            identifier,
            version.toInt(),
            projectId,
            name,
            description,
            startDate,
            endDate,
            status,
            participantId,
            assigneeFirstName,
            assigneeLastName,
            assigneeCompanyName,
            todos.map { it.toQueryResult() }
        )
}

@Embeddable
data class Todo(
    @Embedded @Column(nullable = false) val todoId: TodoId,
    @Column(nullable = false) val description: String,
    @Column(nullable = false) var isDone: Boolean
) : Serializable {
    fun toQueryResult() = TodoQueryResult(todoId, description, isDone)
}
