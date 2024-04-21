package com.novatecgmbh.eventsourcing.axon.common.api

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
data class AggregateReference<T>(@Embedded val identifier: T, val displayName: String? = null)
