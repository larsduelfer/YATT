package com.novatecgmbh.eventsourcing.axon.company.company.api

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class CompanyId(@get:JsonValue val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}
