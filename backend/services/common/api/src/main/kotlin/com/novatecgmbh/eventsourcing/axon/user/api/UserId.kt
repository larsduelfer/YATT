package com.novatecgmbh.eventsourcing.axon.user.api

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class UserId(@get:JsonValue val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}
