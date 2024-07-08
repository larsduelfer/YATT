package com.novatecgmbh.eventsourcing.axon.company.company.query

import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyId
import com.novatecgmbh.eventsourcing.axon.company.company.api.CompanyQueryResult
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "company")
class CompanyProjection(
    @EmbeddedId var identifier: CompanyId,
    @Column(nullable = false) var version: Long,
    @Column(nullable = false) var name: String
) {
    fun toQueryResult() = CompanyQueryResult(identifier, version, name)
}
