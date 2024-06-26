package com.novatecgmbh.eventsourcing.axon.project.task.api

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class TaskId(@get:JsonValue val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

@Embeddable
data class TodoId(@get:JsonValue var identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

enum class TaskStatusEnum {
    PLANNED,
    STARTED,
    COMPLETED
}
