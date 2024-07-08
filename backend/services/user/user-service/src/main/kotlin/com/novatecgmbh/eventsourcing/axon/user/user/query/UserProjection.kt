package com.novatecgmbh.eventsourcing.axon.user.user.query

import com.novatecgmbh.eventsourcing.axon.user.api.UserId
import com.novatecgmbh.eventsourcing.axon.user.api.UserQueryResult
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserProjection(
    @EmbeddedId var identifier: UserId,
    @Column(nullable = false) var externalUserId: String,
    @Column(nullable = false) var firstname: String,
    @Column(nullable = false) var lastname: String,
    @Column(nullable = false) var email: String,
    @Column(nullable = false) var telephone: String
) {
    fun toQueryResult() =
        UserQueryResult(identifier, externalUserId, firstname, lastname, email, telephone)
}
