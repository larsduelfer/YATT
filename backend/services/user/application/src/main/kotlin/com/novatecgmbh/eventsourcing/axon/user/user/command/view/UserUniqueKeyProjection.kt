package com.novatecgmbh.eventsourcing.axon.user.user.command.view

import com.novatecgmbh.eventsourcing.axon.user.api.UserId
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_unique_key")
class UserUniqueKeyProjection(
    @EmbeddedId var identifier: UserId,
    @Column(nullable = false, unique = true) var externalUserId: String,
    @Column(nullable = false, unique = true) var email: String
)
