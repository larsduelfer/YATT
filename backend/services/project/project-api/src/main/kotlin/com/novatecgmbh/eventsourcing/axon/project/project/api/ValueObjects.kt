package com.novatecgmbh.eventsourcing.axon.project.project.api

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class ProjectId(@get:JsonValue val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

enum class ProjectStatus {
    ON_TIME,
    DELAYED
}
